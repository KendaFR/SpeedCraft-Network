package fr.kenda.speedcraft.core;

import fr.kenda.speedcraft.core.command.CommandManager;
import fr.kenda.speedcraft.core.gui.GlobalGUIListener;
import fr.kenda.speedcraft.core.scoreboard.ScoreboardService;
import fr.kenda.speedcraft.core.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public final class SpeedCraftCore extends JavaPlugin {

    @Getter
    private static SpeedCraftCore core;

    @Override
    public void onEnable() {
        core = this;

        Logger.info("Plugin start successfully");

        Bukkit.getPluginManager().registerEvents(new GlobalGUIListener(), this);
        new CommandManager(this, "fr.kenda.speedcraft.core.command").registerAll();
    }

    @Override
    public void onDisable() {
        Logger.info("Plugin stop...");

        ScoreboardService.getINSTANCE().clearBoard();
    }
}