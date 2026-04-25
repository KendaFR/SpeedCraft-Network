package fr.kenda.speedcraft.core.command;

import fr.kenda.speedcraft.core.command.annotation.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Command(
        name = "stps",
        permission = "speedcraft.admin",
        aliases = {"performance", "perf", "stats"}
)
public class TpsCommand extends AbstractCommand {

    private static final int BAR_LENGTH = 20;

    // ── Couleurs ──────────────────────────────────────────────────────────────
    private static final String C_RESET   = "§r";
    private static final String C_TITLE   = "§6§l";   // or-gras
    private static final String C_LABEL   = "§e";     // jaune
    private static final String C_VALUE   = "§f";     // blanc
    private static final String C_DIM     = "§8";     // gris foncé
    private static final String C_SEP     = "§7";     // gris
    private static final String C_ACCENT  = "§b";     // cyan

    private static final String C_GOOD    = "§a";     // vert
    private static final String C_WARN    = "§6";     // orange
    private static final String C_BAD     = "§c";     // rouge

    private static final String C_BAR_FG_GOOD = "§a";
    private static final String C_BAR_FG_WARN = "§6";
    private static final String C_BAR_FG_BAD  = "§c";
    private static final String C_BAR_BG      = "§8";

    private static final String SEP_LINE =
            C_DIM + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    // ── mémoire min/max persistants ──────────────────────────────────────────
    private long peakUsed    = 0L;
    private long minimumUsed = Long.MAX_VALUE;

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {

        // ── TPS ──────────────────────────────────────────────────────────────
        double[] recentTps = Bukkit.getTPS();       // [1m, 5m, 15m]
        double tps1m  = clamp(recentTps[0], 0, 20);
        double tps5m  = clamp(recentTps[1], 0, 20);
        double tps15m = clamp(recentTps[2], 0, 20);

        // ── Mémoire ──────────────────────────────────────────────────────────
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = memBean.getHeapMemoryUsage();

        long usedBytes = heap.getUsed();
        long freeBytes = heap.getCommitted() - heap.getUsed();
        long maxBytes  = heap.getMax() < 0 ? heap.getCommitted() : heap.getMax();

        // mise à jour peak et min
        if (usedBytes > peakUsed)    peakUsed    = usedBytes;
        if (usedBytes < minimumUsed) minimumUsed = usedBytes;

        double memPercent = maxBytes > 0 ? (double) usedBytes / maxBytes * 100.0 : 0;

        // ── Assemblage du message ─────────────────────────────────────────────
        sender.sendMessage("");
        sender.sendMessage(C_TITLE + "  ⚡ SpeedCraft " + C_SEP + "— " + C_ACCENT + "Performances serveur");
        sender.sendMessage(SEP_LINE);

        // Bloc TPS
        sender.sendMessage(C_LABEL + "  TPS (Ticks Per Second)");
        sender.sendMessage(
                C_SEP + "  ├─ " + C_DIM + "1 min  : " + tpsBar(tps1m)  + "  " + tpsColor(tps1m)  + String.format("%.1f", tps1m)  + C_DIM + " / 20"
        );
        sender.sendMessage(
                C_SEP + "  ├─ " + C_DIM + "5 min  : " + tpsBar(tps5m)  + "  " + tpsColor(tps5m)  + String.format("%.1f", tps5m)  + C_DIM + " / 20"
        );
        sender.sendMessage(
                C_SEP + "  └─ " + C_DIM + "15 min : " + tpsBar(tps15m) + "  " + tpsColor(tps15m) + String.format("%.1f", tps15m) + C_DIM + " / 20"
        );

        sender.sendMessage(SEP_LINE);

        // Bloc Mémoire
        sender.sendMessage(C_LABEL + "  Mémoire Heap (JVM)");
        sender.sendMessage(
                C_SEP + "  ├─ " + C_DIM + "Utilisée : " + memBar(memPercent) + "  " + memColor(memPercent) + formatMb(usedBytes) + C_DIM + " / " + formatMb(maxBytes)
        );
        sender.sendMessage(
                C_SEP + "  ├─ " + C_DIM + "Libre    : " + C_GOOD + formatMb(freeBytes)
        );
        sender.sendMessage(
                C_SEP + "  ├─ " + C_DIM + "Peak max : " + C_BAD  + formatMb(peakUsed)
        );
        sender.sendMessage(
                C_SEP + "  └─ " + C_DIM + "Peak min : " + C_GOOD + (minimumUsed == Long.MAX_VALUE ? "N/A" : formatMb(minimumUsed))
        );

        sender.sendMessage(SEP_LINE);

        // Résumé état global
        sender.sendMessage(C_SEP + "  État général : " + overallStatus(tps1m, memPercent));
        sender.sendMessage("");

        return true;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    /** Barre de progression Unicode pour le TPS */
    private String tpsBar(double tps) {
        int filled = (int) Math.round((tps / 20.0) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));
        String color = tps >= 18 ? C_BAR_FG_GOOD : tps >= 14 ? C_BAR_FG_WARN : C_BAR_FG_BAD;
        return color + "█".repeat(filled) + C_BAR_BG + "░".repeat(BAR_LENGTH - filled);
    }

    /** Barre de progression Unicode pour la mémoire */
    private String memBar(double percent) {
        int filled = (int) Math.round((percent / 100.0) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));
        String color = percent < 60 ? C_BAR_FG_GOOD : percent < 80 ? C_BAR_FG_WARN : C_BAR_FG_BAD;
        return color + "█".repeat(filled) + C_BAR_BG + "░".repeat(BAR_LENGTH - filled);
    }

    /** Couleur du TPS affiché */
    private String tpsColor(double tps) {
        if (tps >= 18) return C_GOOD;
        if (tps >= 14) return C_WARN;
        return C_BAD;
    }

    /** Couleur du pourcentage mémoire */
    private String memColor(double percent) {
        if (percent < 60) return C_GOOD;
        if (percent < 80) return C_WARN;
        return C_BAD;
    }

    /** Résumé global avec indicateur emoji */
    private String overallStatus(double tps, double memPercent) {
        if (tps >= 18 && memPercent < 60) return C_GOOD + "✔ Optimal — tout va bien";
        if (tps >= 14 && memPercent < 80) return C_WARN + "⚠ Dégradé — performances réduites";
        return C_BAD + "✘ Critique — intervention recommandée";
    }

    /** Formate des bytes en MiB lisible */
    private String formatMb(long bytes) {
        return String.format("%.0f MB", bytes / (1024.0 * 1024.0));
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}