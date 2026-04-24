package fr.kenda.speedcraft.hub.events.join;

import fr.kenda.speedcraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.api.enumeration.Rank;
import fr.kenda.speedcraft.core.bossbar.BossbarService;
import fr.kenda.speedcraft.core.fastboard.FastBoard;
import fr.kenda.speedcraft.core.scoreboard.ScoreboardService;
import fr.kenda.speedcraft.core.utils.FileConfig;
import fr.kenda.speedcraft.core.utils.itembuilder.ItemBuilder;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        teleportPlayer(player);
        updateScoreboard(player);
        updateBossbar(player);
        updateInventory(player);
    }

    private void updateInventory(Player player) {

        player.getInventory().clear();

        JoinUtils.HUB_ITEMS.forEach(hubItem -> {

            ItemBuilder builder = new ItemBuilder(hubItem.material())
                    .name(hubItem.name())
                    .lore(hubItem.lores());

            ItemStack item;

            if (hubItem.material() == Material.PLAYER_HEAD) {

                item = ItemBuilder.of(Material.PLAYER_HEAD)
                        .skull()
                        .owner(player)
                        .name(hubItem.name())
                        .lore(hubItem.lores())
                        .build();

            } else {
                item = builder.build();
            }

            player.getInventory().setItem(hubItem.slot(), item);
        });
    }

    private void updateBossbar(Player player) {
        BossbarService.getINSTANCE().addPlayer(player);
    }

    private void updateScoreboard(Player player) {
        final FileConfig configMessage = FileConfigurationUtils.CONFIG_MESSAGE;
        FastBoard board = ScoreboardService.getINSTANCE().createBoard(player);
        board.updateTitle(configMessage.getOrDefault("scoreboard.title", "§6§lSpeedCraft", String.class));

        //TODO mettre en config
        List<String> lines = configMessage.getOrDefault(
                "scoreboard.lines",
                Arrays.asList("test", "test2"),
                List.class
        );

        List<String> parsed = lines.stream()
                .map(line -> line
                        .replace("{player}", player.getName())
                        .replace("{rank}", Rank.ADMIN.getChatColor() + Rank.ADMIN.getNameRank())
                        .replace("{lobby_name}", FileConfigurationUtils.HUB_CONFIG.getOrDefault("server_name", UUID.randomUUID().toString(), String.class))
                )
                .toList();

        board.updateLines(parsed);
    }

    private void teleportPlayer(Player player) {
        FileConfig fileConfig = new FileConfig("Hub", "spawn", EExtension.YML);

        double x = fileConfig.getOrDefault("spawn.x", 0., Double.class);
        double y = fileConfig.getOrDefault("spawn.y", 0., Double.class);
        double z = fileConfig.getOrDefault("spawn.z", 0., Double.class);
        double yaw = fileConfig.getOrDefault("spawn.yaw", 0., Double.class);
        double pitch = fileConfig.getOrDefault("spawn.pitch", 180., Double.class);

        player.teleport(new Location(player.getWorld(), x, y, z, (float) yaw, (float) pitch));
    }
}