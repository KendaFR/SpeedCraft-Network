package fr.kenda.speedcraft.hub.services;

import fr.kenda.speecraft.api.service.IService;
import fr.kenda.speedcraft.hub.SpeedCraftHub;
import fr.kenda.speedcraft.hub.events.PlayerJoin;
import fr.kenda.speedcraft.hub.events.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventService implements IService {

    private static EventService instance;

    public static EventService getInstance() {
        if (instance == null)
            instance = new EventService();
        return instance;
    }

    @Override
    public void register() {
        final SpeedCraftHub instance = SpeedCraftHub.getInstance();
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoin(), instance);
        pm.registerEvents(new PlayerQuit(), instance);
    }

    @Override
    public void unregister() {

    }
}
