package fr.kenda.speedcraftproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.kenda.speedcraftproxy.services.EventService;
import lombok.Getter;
import org.slf4j.Logger;

@Getter
@Plugin(
        id = "speedcraft-proxy",
        name = "SpeedCraft-Proxy",
        version = "1.0",
        authors = {"Kenda"}
)
public class SpeedCraftProxy {

    @Inject @Getter private Logger logger;

    @Inject @Getter private ProxyServer server;

    @Getter
    private static SpeedCraftProxy instance;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        logger.info("Plugin initialize");

        EventService.getInstance().register();
    }

    @Subscribe
    public void OnProxyShutdown(ProxyShutdownEvent event)
    {
        logger.info("Plugin shutdown...");
    }

}
