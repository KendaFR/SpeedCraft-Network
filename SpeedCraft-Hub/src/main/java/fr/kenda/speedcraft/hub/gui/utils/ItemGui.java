package fr.kenda.speedcraft.hub.gui.utils;

import fr.kenda.speedcraft.hub.config.HubPaths;
import fr.kenda.speedcraft.hub.events.join.HubItem;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Material;

import java.util.List;

@SuppressWarnings("unchecked")
public class ItemGui {

    public static final List<HubItem> GUI_MENU_ITEMS = List.of(

            new HubItem(
                    FileConfigurationUtils.CONFIG_GUI.getOrDefault(
                            HubPaths.GUI_GAME_TRAINING_TIME_RANDOM_SEED, "§Training speed", String.class),
                    FileConfigurationUtils.CONFIG_GUI.getOrDefault(
                            HubPaths.GUI_GAME_TRAINING_TIME_RANDOM_SEED_LORE, List.of("§Training speed"), List.class),
                    Material.COMPASS,
                    12,
                    player -> {
                        player.closeInventory();
                        player.sendMessage("§aTest training mode");
                    }
            )
    );
}