package fr.kenda.speedcraft.core.bossbar;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossbarService {

    @Getter
    private static final BossbarService INSTANCE = new BossbarService();

    private BossBar headerBar;

    public void createBossBar(String msg, BarColor color, BarStyle style) {
        headerBar = Bukkit.createBossBar(msg, color, style);
    }

    public void updateTextBossBar(String text) {

        if (headerBar == null)
            createBossBar(text, BarColor.BLUE, BarStyle.SOLID);
        headerBar.setTitle(text);
    }

    public void updateStyleBar(BarStyle style) {
        if (headerBar == null)
            createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        headerBar.setStyle(style);
    }

    public void updateColorBar(BarColor color) {
        if (headerBar == null)
            createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        headerBar.setColor(color);
    }

    public void setFilledBar(float percent) {
        if (headerBar == null)
            createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        headerBar.setProgress(percent / 100f);
    }

    public void setFilledBarNormalized(float percentNormalized) {
        if (headerBar == null)
            createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        headerBar.setProgress(percentNormalized);
    }

    public void addPlayer(Player player) {
        headerBar.addPlayer(player);
    }

    public void removePlayer(Player player) {
        headerBar.removePlayer(player);
    }
}