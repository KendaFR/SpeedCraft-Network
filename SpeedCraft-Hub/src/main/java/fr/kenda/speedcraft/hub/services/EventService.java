package fr.kenda.speedcraft.hub.services;

import fr.kenda.speedcraft.api.service.IService;
import fr.kenda.speedcraft.core.events.CoreEventService;
import fr.kenda.speedcraft.hub.events.BlockedEvent;
import fr.kenda.speedcraft.hub.events.PlayerInteract;
import fr.kenda.speedcraft.hub.events.PlayerQuit;
import fr.kenda.speedcraft.hub.events.join.PlayerJoin;
import org.bukkit.event.Listener;

import java.util.Set;

public class EventService extends CoreEventService implements IService {

    @Override
    public void register() {
        Set<Listener> events = Set.of(
                new PlayerJoin(), new PlayerQuit(), new BlockedEvent(), new PlayerInteract()
        );
        registerEvents(events);
    }

    @Override
    public void unregister() {

    }
}
