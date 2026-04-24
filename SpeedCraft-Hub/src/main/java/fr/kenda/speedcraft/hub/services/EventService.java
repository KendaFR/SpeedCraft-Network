package fr.kenda.speedcraft.hub.services;

import fr.kenda.speedcraft.api.service.IService;
import fr.kenda.speedcraft.hub.SpeedCraftHub;
import fr.kenda.speedcraft.hub.events.BlockedEvent;
import fr.kenda.speedcraft.hub.events.PlayerInteract;
import fr.kenda.speedcraft.hub.events.PlayerQuit;
import fr.kenda.speedcraft.hub.events.join.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;

public class EventService implements IService {

    private static EventService instance;

    public static EventService getInstance() {
        if (instance == null)
            instance = new EventService();
        return instance;
    }

    @Override
    public void register() {
        SpeedCraftHub instance = SpeedCraftHub.getInstance();
        PluginManager pm = Bukkit.getPluginManager();

        Set<Listener> events = Set.of(
                new PlayerJoin(), new PlayerQuit(), new BlockedEvent(), new PlayerInteract()
        );

        events.forEach(listener -> pm.registerEvents(listener, instance));
    }

    @Override
    public void unregister() {

    }
}
