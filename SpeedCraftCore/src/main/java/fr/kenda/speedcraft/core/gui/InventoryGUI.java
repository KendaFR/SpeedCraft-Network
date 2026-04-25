package fr.kenda.speedcraft.core.gui;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class InventoryGUI {

    protected final Inventory inventory;
    protected final Player player;

    public InventoryGUI(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(
                null,
                sizeInventory(),
                Component.text(nameInventory())
        );

        updateContent(initializeItems());
    }

    protected abstract String nameInventory();

    protected abstract int sizeInventory();

    protected abstract ItemStack[] initializeItems();

    public void onOpen(Player player) {
    }

    public void onClose(Player player) {
    }

    public void updateContent(ItemStack[] content) {
        inventory.clear();

        for (int i = 0; i < Math.min(content.length, inventory.getSize()); i++) {
            if (content[i] != null) {
                inventory.setItem(i, content[i]);
            }
        }
    }

    public void onClick(Player player, int slot, InventoryClickEvent event) {
        // override optionnel
    }
}