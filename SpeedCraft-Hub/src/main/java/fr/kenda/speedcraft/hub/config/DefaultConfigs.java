package fr.kenda.speedcraft.hub.config;

import fr.kenda.speedcraft.core.utils.FileConfig;

import java.util.*;

public class DefaultConfigs {

    public static final Map<String, Object> DEFAULTS_GUI = new HashMap<>();
    public static final Map<String, Object> DEFAULTS_MESSAGES = new HashMap<>();
    public static final Map<String, Object> DEFAULTS_PROPERTIES = new HashMap<>();

    static {

        // =========================
        // HUB ITEMS
        // =========================

        DEFAULTS_MESSAGES.put(HubPaths.HUB_SELECTOR, "§6Sélecteur de jeux");
        DEFAULTS_MESSAGES.put(HubPaths.HUB_SELECTOR_LORE, List.of(
                "§7Clique pour choisir un mode",
                "§7de jeu disponible"
        ));

        DEFAULTS_MESSAGES.put(HubPaths.HUB_DISCORD, "§9Discord");
        DEFAULTS_MESSAGES.put(HubPaths.HUB_DISCORD_LORE, List.of(
                "§7Rejoins notre communauté",
                "§7et échange avec les joueurs"
        ));

        DEFAULTS_MESSAGES.put(HubPaths.HUB_FRIENDS, "§aAmis");
        DEFAULTS_MESSAGES.put(HubPaths.HUB_FRIENDS_LORE, List.of(
                "§7Gère ta liste d'amis",
                "§7et joue ensemble"
        ));

        DEFAULTS_MESSAGES.put(HubPaths.HUB_SETTINGS, "§eParamètres");
        DEFAULTS_MESSAGES.put(HubPaths.HUB_SETTINGS_LORE, List.of(
                "§7Configure ton expérience",
                "§7personnalisée"
        ));

        DEFAULTS_MESSAGES.put(HubPaths.HUB_SHOP, "§bBoutique");
        DEFAULTS_MESSAGES.put(HubPaths.HUB_SHOP_LORE, List.of(
                "§7Achète des cosmétiques",
                "§7et des avantages"
        ));

        // =========================
        // SCOREBOARD
        // =========================

        DEFAULTS_MESSAGES.put(HubPaths.SCOREBOARD_TITLE, "§6§lSpeedCraft");

        DEFAULTS_MESSAGES.put(HubPaths.SCOREBOARD_LINES, List.of(
                "§7Joueur: " + FileConfig.createPlaceholder("player"),
                "§7Serveur: " + FileConfig.createPlaceholder("lobby_name")
        ));

        DEFAULTS_MESSAGES.put(HubPaths.GLOBAL_WIP, "§cWork In Progress...");

        // =========================
        // GUI
        // =========================

        DEFAULTS_GUI.put(HubPaths.GUI_GAME_TITLE, "§6Jeux");
        DEFAULTS_GUI.put(HubPaths.GUI_GAME_SIZE, 54);

        DEFAULTS_GUI.put(HubPaths.TRAINING_FRAME, "§aTraining");
        DEFAULTS_GUI.put(HubPaths.RANKED_FRAME, "§6Ranked");

        DEFAULTS_GUI.put(HubPaths.GUI_GAME_TRAINING_TIME_RANDOM_SEED, "§aSpeedrun à temps !");
        DEFAULTS_GUI.put(HubPaths.GUI_GAME_TRAINING_TIME_RANDOM_SEED_LORE, List.of("§7Entraînez-vous au speedrun", "§7avec une seed random !", "", "§7Joueurs: §e" + FileConfig.createPlaceholder("players"), "§7Dev: §6Kenda"));

        DEFAULTS_GUI.put(HubPaths.GUI_GAME_TRAINING_RANDOM_ITEM_RANDOM_SEED, "§aSpeedrun un item !");
        DEFAULTS_GUI.put(HubPaths.GUI_GAME_TRAINING_RANDOM_ITEM_RANDOM_SEED_LORE, List.of("§7Entraînez-vous à speedrun un item", "§7avec une seed random !", "", "§7Joueurs: §e" + FileConfig.createPlaceholder("players"), "§7Dev: §6Kenda"));

        // =========================
        // SPAWN
        // =========================

        DEFAULTS_PROPERTIES.put(HubPaths.SERVER_ID, UUID.randomUUID().toString());

        DEFAULTS_PROPERTIES.put("spawn.x", 0.0);
        DEFAULTS_PROPERTIES.put("spawn.y", -63.0);
        DEFAULTS_PROPERTIES.put("spawn.z", 0.0);
        DEFAULTS_PROPERTIES.put("spawn.yaw", 0.0);
        DEFAULTS_PROPERTIES.put("spawn.pitch", 0.0);

    }
}