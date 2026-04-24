package fr.kenda.speedcraft.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GlobalGUIListener implements Listener {

    private final GUIManager manager;

    public GlobalGUIListener(GUIManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        InventoryGUI gui = manager.getOpenGUI(player);
        if (gui == null) return;

        if (!event.getView().getTopInventory().equals(gui.getInventory())) return;

        event.setCancelled(true);

        onGUIClick(player, gui, event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) return;

        InventoryGUI gui = manager.getOpenGUI(player);
        if (gui == null) return;

        manager.closeGUI(player);
    }

    protected void onGUIClick(Player player, InventoryGUI gui, InventoryClickEvent event) {
        // override si besoin
    }
}