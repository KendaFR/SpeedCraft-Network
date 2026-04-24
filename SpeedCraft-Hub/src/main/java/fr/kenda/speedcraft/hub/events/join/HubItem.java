package fr.kenda.speedcraft.hub.events.join;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public record HubItem(
        String name,
        Material material,
        List<String> lores,
        int slot,
        Consumer<Player> action
) {}
