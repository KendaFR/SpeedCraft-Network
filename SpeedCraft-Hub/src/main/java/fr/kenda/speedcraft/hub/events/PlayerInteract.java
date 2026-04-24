package fr.kenda.speedcraft.hub.events;

import fr.kenda.speedcraft.core.utils.Logger;
import fr.kenda.speedcraft.hub.events.join.JoinUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteract implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();

        int slot = player.getInventory().getHeldItemSlot();

        JoinUtils.HUB_ITEMS.forEach(hubItem -> {
            if (slot == hubItem.slot()) {
                hubItem.action().accept(player);
            }
        });
    }
}