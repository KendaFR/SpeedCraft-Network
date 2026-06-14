package fr.kenda.speedcraftproxy.services;

import com.velocitypowered.api.proxy.ProxyServer;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.events.PlayerJoin;
import lombok.Getter;

public class EventService {

    @Getter
    private static final EventService INSTANCE = new EventService();

    public void register() {
        final ProxyServer server = SpeedCraftProxy.getInstance().getServer();

        server.getEventManager().register(
                SpeedCraftProxy.getInstance(),
                new PlayerJoin()
        );
    }
}
