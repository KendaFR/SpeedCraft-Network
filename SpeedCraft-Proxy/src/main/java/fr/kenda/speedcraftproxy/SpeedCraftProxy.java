package fr.kenda.speedcraftproxy;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import fr.kenda.speedcraftproxy.services.CommandService;
import fr.kenda.speedcraftproxy.services.EventService;
import fr.kenda.speedcraftproxy.services.ServerService;
import lombok.Getter;
import org.slf4j.Logger;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Plugin(
        id = "speedcraft-proxy",
        name = "SpeedCraft-Proxy",
        version = "1.0",
        authors = {"Kenda"}
)
public class SpeedCraftProxy {

    @Inject
    @Getter
    private Logger logger;

    @Inject
    @Getter
    private ProxyServer server;

    @Getter
    private static SpeedCraftProxy instance;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        logger.info("Plugin initialize");

        EventService.getINSTANCE().register();
        CommandService.getINSTANCE().registerCommands();
        ServerService.getINSTANCE().init();

        server.getServer("placeholder").ifPresent(registeredServer ->
                server.unregisterServer(registeredServer.getServerInfo()));
    }

    @Subscribe
    public void OnProxyShutdown(ProxyShutdownEvent event) {
        logger.info("Plugin shutdown...");
        ServerService.getINSTANCE().shutdown();

        logger.info("Tous les serveurs arrêtés.");
    }
}