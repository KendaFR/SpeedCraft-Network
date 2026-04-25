package fr.kenda.speedcraft.core.events;

import fr.kenda.speedcraft.core.SpeedCraftCore;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;

public class CoreEventService {

    @Getter
    private static final CoreEventService INSTANCE = new CoreEventService();

    public void registerEvent(Listener listener) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(listener, SpeedCraftCore.getCore());
    }

    public void registerEvents(Set<Listener> listener) {
        PluginManager pm = Bukkit.getPluginManager();
        listener.forEach(event -> pm.registerEvents(event, SpeedCraftCore.getCore()));
    }
}