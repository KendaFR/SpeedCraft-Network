package fr.kenda.speedcraft.hub.events;

import fr.kenda.speedcraft.core.bossbar.BossbarService;
import fr.kenda.speedcraft.core.scoreboard.ScoreboardService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ScoreboardService.getINSTANCE().removeBoard(player);
        BossbarService.getINSTANCE().removePlayer(player);
    }
}