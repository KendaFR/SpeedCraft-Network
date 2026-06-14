package fr.kenda.speedcraftproxy.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.server.EServerType;
import fr.kenda.speedcraftproxy.server.ServerInfo;
import fr.kenda.speedcraftproxy.services.ServerService;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class PlayerJoin {

    @Subscribe
    public void onJoin(PostLoginEvent event) {
        Player player = event.getPlayer();

        // Si le joueur n'est pas sur un serveur, on le connecte manuellement
        if (player.getCurrentServer().isEmpty()) {
            Optional<ServerInfo> server = ServerService.getINSTANCE().getServerByType(EServerType.HUB);

            server.flatMap(serv -> SpeedCraftProxy.getInstance().getServer().getServer(serv.getName())).ifPresent(registeredServer -> {
                player.createConnectionRequest(registeredServer).fireAndForget();
            });
        }
    }
}