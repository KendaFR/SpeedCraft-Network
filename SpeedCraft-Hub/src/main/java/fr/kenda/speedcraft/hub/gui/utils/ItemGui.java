package fr.kenda.speedcraft.hub.gui.utils;

import fr.kenda.speedcraft.hub.events.join.HubItem;
import org.bukkit.Material;

import java.util.Arrays;

public class ItemGui {

    public static final HubItem FRAME_GUI = new HubItem(
            "§6Sélecteur de jeux",
            Material.BLACK_STAINED_GLASS_PANE,
            Arrays.asList(
                    "§7Clique pour choisir un mode",
                    "§7de jeu disponible"
            ),
            -1, player -> {
    }
    );
}