package fr.kenda.speedcraft.hub.events.join;

import fr.kenda.speedcraft.core.gui.GUIManager;
import fr.kenda.speedcraft.hub.config.HubPaths;
import fr.kenda.speedcraft.hub.gui.GameGUI;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class JoinUtils {

    public static final List<HubItem> HUB_ITEMS = Arrays.asList(

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SELECTOR,
                            "§6Sélecteur",
                            String.class
                    ),
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SELECTOR_LORE,
                            List.of("§7Default lore"),
                            List.class
                    ),
                    Material.NETHER_STAR,
                    0,
                    player -> GUIManager.getINSTANCE().openGUI(player, new GameGUI(player))
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_DISCORD,
                            "§9Discord",
                            String.class
                    ),
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_DISCORD_LORE,
                            List.of("§7Rejoindre le Discord"),
                            List.class
                    ),
                    Material.COMPASS,
                    8,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                                    HubPaths.GLOBAL_WIP,
                                    "§cWork In Progress...",
                                    String.class
                            )
                    )
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_FRIENDS,
                            "§aFriends",
                            String.class
                    ),
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_FRIENDS_LORE,
                            List.of("§7Gestion des amis"),
                            List.class
                    ),
                    Material.PLAYER_HEAD,
                    2,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                                    HubPaths.GLOBAL_WIP,
                                    "§cWork In Progress...",
                                    String.class
                            )
                    )
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SETTINGS,
                            "§eSettings",
                            String.class
                    ),
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SETTINGS_LORE,
                            List.of("§7Configurer le jeu"),
                            List.class
                    ),
                    Material.REDSTONE_TORCH,
                    7,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                                    HubPaths.GLOBAL_WIP,
                                    "§cWork In Progress...",
                                    String.class
                            )
                    )
            ),

            new HubItem(
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SHOP,
                            "§6Shop",
                            String.class
                    ),
                    FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                            HubPaths.HUB_SHOP_LORE,
                            List.of("§7Acheter des items"),
                            List.class
                    ),
                    Material.GOLD_INGOT,
                    1,
                    player -> player.sendMessage(
                            FileConfigurationUtils.CONFIG_MESSAGE.getOrDefault(
                                    HubPaths.GLOBAL_WIP,
                                    "§cWork In Progress...",
                                    String.class
                            )
                    )
            )
    );
}