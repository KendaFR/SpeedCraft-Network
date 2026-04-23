package fr.kenda.speedcraftproxy.services;

import com.velocitypowered.api.proxy.ProxyServer;
import fr.kenda.speecraft.api.service.IService;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.events.PlayerJoin;

public class EventService implements IService {

    private static EventService instance;

    public static EventService getInstance() {
        if(instance == null)
            instance = new EventService();
        return instance;
    }

    @Override
    public void register() {
        final ProxyServer server = SpeedCraftProxy.getInstance().getServer();

        server.getEventManager().register(
                SpeedCraftProxy.getInstance(),
                new PlayerJoin()
        );
    }

    @Override
    public void unregister() {

    }
}
