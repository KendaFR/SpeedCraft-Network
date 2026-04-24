package fr.kenda.speedcraft.hub.gui;

import fr.kenda.speedcraft.core.gui.InventoryGUI;
import fr.kenda.speedcraft.core.utils.itembuilder.ItemBuilder;
import fr.kenda.speedcraft.hub.gui.utils.ItemGui;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GameGUI extends InventoryGUI {
    public GameGUI(Player player) {
        super(player);
    }

    @Override
    protected String nameInventory() {
        return FileConfigurationUtils.CONFIG_GUI.getOrDefault("gui.game.title", "§6Jeux", String.class);
    }

    @Override
    protected int sizeInventory() {
        return FileConfigurationUtils.CONFIG_GUI.getOrDefault("gui.game.size", 5 * 9, Integer.class);
    }

    @Override
    protected ItemStack[] initializeItems() {
        ItemStack[] content = new ItemStack[sizeInventory()];

        final List<Integer> slotFrame = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36,37,38,39,40,41,42,43,44);
        slotFrame.forEach(integer -> content[integer] = new ItemBuilder(ItemGui.FRAME_GUI.material()).name(ItemGui.FRAME_GUI.name()).lore(ItemGui.FRAME_GUI.lores()).build());

        return content;
    }
}
