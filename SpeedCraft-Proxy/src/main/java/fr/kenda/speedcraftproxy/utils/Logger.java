package fr.kenda.speedcraftproxy.utils;

import fr.kenda.speedcraftproxy.SpeedCraftProxy;

public class Logger {

    public static void info(String msg)
    {
        SpeedCraftProxy.getInstance().getLogger().info(msg);
    }
    public static void warn(String msg)
    {
        SpeedCraftProxy.getInstance().getLogger().warn(msg);
    }
    public static void error(String msg)
    {
        SpeedCraftProxy.getInstance().getLogger().error(msg);
    }
}
