package fr.kenda.speedcraftproxy.utils;

import fr.kenda.speedcraftproxy.server.ServerInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Messages {


    public static Component success(String msg) {
        return Component.text("✔ " + msg, NamedTextColor.GREEN);
    }

    public static Component error(String msg) {
        return Component.text("✘ " + msg, NamedTextColor.RED);
    }

    public static Component info(String msg) {
        return Component.text("ℹ " + msg, NamedTextColor.AQUA);
    }

    public static Component usage(String msg) {
        return Component.text("➜ " + msg, NamedTextColor.YELLOW);
    }

    public static NamedTextColor statusColor(ServerInfo.ServerStatus status) {
        return switch (status) {
            case STARTING -> NamedTextColor.YELLOW;
            case RUNNING -> NamedTextColor.GREEN;
            case STOPPING -> NamedTextColor.RED;
        };
    }
}
