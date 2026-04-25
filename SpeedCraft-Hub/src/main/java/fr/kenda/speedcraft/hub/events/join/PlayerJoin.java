package fr.kenda.speedcraft.hub.events.join;

import fr.kenda.speedcraft.api.enumeration.Rank;
import fr.kenda.speedcraft.core.bossbar.BossbarService;
import fr.kenda.speedcraft.core.fastboard.FastBoard;
import fr.kenda.speedcraft.core.scoreboard.ScoreboardService;
import fr.kenda.speedcraft.core.utils.FileConfig;
import fr.kenda.speedcraft.hub.config.HubPaths;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        resetPlayer(player);
        teleportPlayer(player);
        updateScoreboard(player);
        updateBossbar(player);
        updateInventory(player);
    }

    private void resetPlayer(Player player) {
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    private void updateInventory(Player player) {
        player.getInventory().clear();

        JoinUtils.HUB_ITEMS.forEach(item ->
                player.getInventory().setItem(item.slot(), item.build(player))
        );
    }

    private void updateBossbar(Player player) {
        BossbarService.getINSTANCE().addPlayer(player);
    }

    private void updateScoreboard(Player player) {

        FastBoard board = ScoreboardService.getINSTANCE().createBoard(player);

        board.updateTitle(
                FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                        HubPaths.SCOREBOARD_TITLE,
                        "§6§lSpeedCraft",
                        String.class
                )
        );

        List<String> lines = FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                HubPaths.SCOREBOARD_LINES,
                List.of("test"),
                List.class
        );

        board.updateLines(lines.stream()
                .map(line -> line
                        .replace(FileConfig.createPlaceholder("player"), player.getName())
                        .replace(FileConfig.createPlaceholder("rank"), Rank.ADMIN.getChatColor() + Rank.ADMIN.getNameRank())
                        .replace(FileConfig.createPlaceholder("lobby_name"),
                                FileConfigurationUtils.HUB_PROPERTIES.getOrDefault(
                                        HubPaths.SERVER_ID,
                                        UUID.randomUUID().toString(),
                                        String.class
                                )
                        )
                ).toList()
        );
    }

    private void teleportPlayer(Player player) {

        FileConfig config = FileConfigurationUtils.HUB_PROPERTIES;

        player.teleport(new Location(
                player.getWorld(),
                config.getOrDefault("spawn.x", 0.0, Double.class),
                config.getOrDefault("spawn.y", 0.0, Double.class),
                config.getOrDefault("spawn.z", 0.0, Double.class),
                config.getOrDefault("spawn.yaw", 0.0, Double.class).floatValue(),
                config.getOrDefault("spawn.pitch", 180.0, Double.class).floatValue()
        ));
    }
}