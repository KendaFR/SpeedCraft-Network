package fr.kenda.speedcraft.hub;

import fr.kenda.speecraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.core.bossbar.BossbarService;
import fr.kenda.speedcraft.core.scheduler.SchedulerService;
import fr.kenda.speedcraft.core.utils.FileConfig;
import fr.kenda.speedcraft.core.utils.TimeUnitMillis;
import fr.kenda.speedcraft.core.utils.TimeUnitTick;
import fr.kenda.speedcraft.hub.scheduler.BossbarChangeTextScheduler;
import fr.kenda.speedcraft.hub.services.EventService;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Boss;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class SpeedCraftHub extends JavaPlugin {

    private static SpeedCraftHub instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getWorlds().forEach(w -> w.setAutoSave(false));
        EventService.getInstance().register();

        final SchedulerService schedulerService = SchedulerService.getINSTANCE();
        schedulerService.registerScheduler("BossBarChange", new BossbarChangeTextScheduler(FileConfigurationUtils.HUB_CONFIG.getOrDefault("time_to_change", 10, Integer.class)));
        schedulerService.runScheduler("BossBarChange", 0, TimeUnitTick.SECONDS.getTicks());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static SpeedCraftHub getInstance() {
        return instance;
    }
}