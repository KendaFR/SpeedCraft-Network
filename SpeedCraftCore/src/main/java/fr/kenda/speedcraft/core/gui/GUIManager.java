package fr.kenda.speedcraft.core.gui;

import fr.kenda.speedcraft.core.SpeedCraftCore;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager implements Listener {
    @Getter
    private static final GUIManager INSTANCE = new GUIManager();
    private final Map<UUID, InventoryGUI> openGUIs = new HashMap<>();

    public void openGUI(Player player, InventoryGUI gui) {

        closeGUI(player);

        openGUIs.put(player.getUniqueId(), gui);

        gui.onOpen(player);
        player.openInventory(gui.getInventory());
    }

    public void closeGUI(Player player) {
        InventoryGUI gui = openGUIs.remove(player.getUniqueId());

        if (gui != null) {
            gui.onClose(player);
        }

        player.closeInventory();
    }

    public InventoryGUI getOpenGUI(Player player) {
        return openGUIs.get(player.getUniqueId());
    }

    public boolean hasOpenGUI(Player player) {
        return openGUIs.containsKey(player.getUniqueId());
    }

    public void cleanup() {
        for (UUID uuid : openGUIs.keySet()) {
            Player player = SpeedCraftCore.getCore().getServer().getPlayer(uuid);
            if (player != null) {
                player.closeInventory();
            }
        }
        openGUIs.clear();
    }
}