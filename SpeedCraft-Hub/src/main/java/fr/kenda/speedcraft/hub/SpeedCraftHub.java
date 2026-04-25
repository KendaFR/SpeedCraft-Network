package fr.kenda.speedcraft.hub;

import fr.kenda.speedcraft.core.command.CommandManager;
import fr.kenda.speedcraft.core.scheduler.SchedulerService;
import fr.kenda.speedcraft.core.utils.ConfigAutoGenerator;
import fr.kenda.speedcraft.core.utils.time.TimeUnitTick;
import fr.kenda.speedcraft.hub.scheduler.BossbarChangeTextScheduler;
import fr.kenda.speedcraft.hub.services.EventService;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static fr.kenda.speedcraft.hub.config.DefaultConfigs.*;

public final class SpeedCraftHub extends JavaPlugin {

    @Getter
    private static SpeedCraftHub instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getWorlds().forEach(w -> w.setAutoSave(false));
        ConfigAutoGenerator.generate(DEFAULTS_GUI, FileConfigurationUtils.CONFIG_GUI);
        ConfigAutoGenerator.generate(DEFAULTS_MESSAGES, FileConfigurationUtils.CONFIG_MESSAGE);
        ConfigAutoGenerator.generate(DEFAULTS_PROPERTIES, FileConfigurationUtils.HUB_PROPERTIES);
        new EventService().register();
        new CommandManager(this, "fr.kenda.speedcraft.hub").registerAll();

        final SchedulerService schedulerService = SchedulerService.getINSTANCE();
        schedulerService.registerScheduler("BossBarChange", new BossbarChangeTextScheduler(FileConfigurationUtils.HUB_PROPERTIES.getOrDefault("time_to_change", 10, Integer.class)));
        schedulerService.runScheduler("BossBarChange", 0, TimeUnitTick.SECONDS.getTicks());
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}