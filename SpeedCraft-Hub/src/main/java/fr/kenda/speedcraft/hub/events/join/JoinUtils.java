package fr.kenda.speedcraft.hub.events.join;

import fr.kenda.speedcraft.core.gui.GUIManager;
import fr.kenda.speedcraft.hub.gui.GameGUI;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class JoinUtils {

    public static final List<HubItem> HUB_ITEMS = Arrays.asList(

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.selector.name", "§6Sélecteur de jeux", String.class),
                    Material.NETHER_STAR,
                    Arrays.asList(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.selector.lore.1", "§7Clique pour choisir un mode", String.class),
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.selector.lore.2", "§7de jeu disponible", String.class)
                    ),
                    0,
                    player -> GUIManager.getINSTANCE().openGUI(player, new GameGUI(player))
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.discord.name", "§9Discord", String.class),
                    Material.COMPASS,
                    Arrays.asList(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.discord.lore.1", "§7Rejoins notre communauté", String.class),
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.discord.lore.2", "§7et échange avec les joueurs", String.class)
                    ),
                    8,
                    player -> {
                        // à compléter plus tard
                    }
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.friends.name", "§aAmis", String.class),
                    Material.PLAYER_HEAD,
                    Arrays.asList(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.friends.lore.1", "§7Gère ta liste d'amis", String.class),
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.friends.lore.2", "§7et joue ensemble", String.class)
                    ),
                    2,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("global.work_in_progress", "§cWork In Progress...", String.class)
                    )
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.settings.name", "§eParamètres", String.class),
                    Material.REDSTONE_TORCH,
                    Arrays.asList(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.settings.lore.1", "§7Configure ton expérience", String.class),
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.settings.lore.2", "§7personnalisée", String.class)
                    ),
                    7,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("global.work_in_progress", "§cWork In Progress...", String.class)
                    )
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.shop.name", "§bBoutique", String.class),
                    Material.GOLD_INGOT,
                    Arrays.asList(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.shop.lore.1", "§7Achète des cosmétiques", String.class),
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("hub.shop.lore.2", "§7et des avantages", String.class)
                    ),
                    1,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault("global.work_in_progress", "§cWork In Progress...", String.class)
                    )
            )
    );
}