package fr.kenda.speedcraftproxy.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;

public class PlayerJoin {

    @Subscribe
    public void onJoin(PostLoginEvent event)
    {
        Player player = event.getPlayer();
        SpeedCraftProxy.getInstance().getLogger().info("Player joined: {}", player.getUsername());
    }
}
