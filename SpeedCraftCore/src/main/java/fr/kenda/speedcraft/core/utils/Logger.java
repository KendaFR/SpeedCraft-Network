package fr.kenda.speedcraft.core.utils;

import fr.kenda.speedcraft.core.SpeedCraftCore;

public class Logger {

    public static void info(String msg) {
        SpeedCraftCore.getCore().getLogger().info(msg);
    }

    public static void warn(String msg) {
        SpeedCraftCore.getCore().getLogger().warning(msg);
    }

    public static void error(String msg) {
        SpeedCraftCore.getCore().getLogger().severe(msg);
    }
}
