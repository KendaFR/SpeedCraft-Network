package fr.kenda.speedcraft.hub.utils;

import fr.kenda.speecraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.core.utils.FileConfig;

public class FileConfigurationUtils {
    public static final FileConfig CONFIG_MESSAGE =  new FileConfig("Hub", "messages", EExtension.YML);
    public static final FileConfig HUB_CONFIG =  new FileConfig("hub_properties", "messages", EExtension.SETTINGS);
}
