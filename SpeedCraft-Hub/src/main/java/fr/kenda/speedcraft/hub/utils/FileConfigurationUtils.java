package fr.kenda.speedcraft.hub.utils;

import fr.kenda.speedcraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.core.utils.FileConfig;

public class FileConfigurationUtils {
    public static final FileConfig CONFIG_MESSAGE = new FileConfig("Hub", "messages", EExtension.YML);
    public static final FileConfig CONFIG_GUI = new FileConfig("Hub", "gui", EExtension.YML);
    public static final FileConfig HUB_PROPERTIES = new FileConfig("Hub", "hub_properties", EExtension.SETTINGS);
}
