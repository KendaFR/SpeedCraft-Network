package fr.kenda.speedcraft.hub.gui;

import fr.kenda.speedcraft.core.gui.InventoryGUI;
import fr.kenda.speedcraft.core.utils.itembuilder.ItemBuilder;
import fr.kenda.speedcraft.hub.config.HubPaths;
import fr.kenda.speedcraft.hub.gui.utils.ItemGui;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GameGUI extends InventoryGUI {

    public GameGUI(Player player) {
        super(player);
    }

    @Override
    protected String nameInventory() {
        return FileConfigurationUtils.CONFIG_GUI
                .getOrDefault(HubPaths.GUI_GAME_TITLE, "§6Jeux", String.class);
    }

    @Override
    protected int sizeInventory() {
        return FileConfigurationUtils.CONFIG_GUI
                .getOrDefault(HubPaths.GUI_GAME_SIZE, 54, Integer.class);
    }

    @Override
    protected ItemStack[] initializeItems() {

        int size = sizeInventory();
        ItemStack[] content = new ItemStack[size];

        ItemStack frameItem = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .name(" ")
                .build();

        ItemStack frameTraining = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE)
                .name(FileConfigurationUtils.CONFIG_GUI
                        .getOrDefault(HubPaths.TRAINING_FRAME, "§aTraining", String.class))
                .build();

        ItemStack frameRanked = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE)
                .name(FileConfigurationUtils.CONFIG_GUI
                        .getOrDefault(HubPaths.RANKED_FRAME, "§6Ranked", String.class))
                .build();

        apply(content, size, frameItem,
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17,
                18, 26,
                27, 35,
                36, 44,
                45, 46, 47, 48, 49, 50, 51, 52, 53,
                10, 16, 37, 43,
                13, 22, 31, 40
        );

        apply(content, size, frameTraining,
                11, 12,
                19, 20, 21,
                28, 29, 30,
                38, 39
        );

        apply(content, size, frameRanked,
                14, 15,
                23, 24, 25,
                32, 33, 34,
                41, 42
        );

        // =========================
        // ITEMS (OPTIMISÉ)
        // =========================
        ItemGui.GUI_MENU_ITEMS.forEach(item ->
                content[item.slot()] = item.build(
                        player,
                        Map.of("{players}", String.valueOf(0))
                )
        );

        return content;
    }

    @Override
    public void onClick(Player player, int slot, InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        ItemGui.GUI_MENU_ITEMS.stream()
                .filter(i -> i.slot() == slot)
                .findFirst().ifPresent(item -> item.action().accept(player));

    }

    private void apply(ItemStack[] content, int size, ItemStack item, int... slots) {

        for (int slot : slots) {
            if (slot >= 0 && slot < size) {
                content[slot] = item;
            }
        }
    }
}