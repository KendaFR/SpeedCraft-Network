package fr.kenda.speedcraft.core;

import fr.kenda.speedcraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.core.gui.GlobalGUIListener;
import fr.kenda.speedcraft.core.scoreboard.ScoreboardService;
import fr.kenda.speedcraft.core.utils.FileConfig;
import fr.kenda.speedcraft.core.utils.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpeedCraftCore extends JavaPlugin {

    @Getter
    private static SpeedCraftCore core;

    @Override
    public void onEnable() {
        core = this;

        Logger.info("Plugin start successfully");

        Bukkit.getPluginManager().registerEvents(new GlobalGUIListener(), this);

        FileConfig databaseConfig = new FileConfig("", "credentials", EExtension.YML);
        /*Database.init(new DatabaseCredentials(
                databaseConfig.getOrDefault("database.host", "host", String.class),
                databaseConfig.getOrDefault("database.port", 3306, Integer.class),
                databaseConfig.getOrDefault("database.database", "database", String.class),
                databaseConfig.getOrDefault("database.username", "username", String.class),
                databaseConfig.getOrDefault("database.password", "password", String.class)));*/
    }

    @Override
    public void onDisable() {
        Logger.info("Plugin stop...");

        ScoreboardService.getINSTANCE().clearBoard();
        //Database.getInstance().shutdown();
    }
}