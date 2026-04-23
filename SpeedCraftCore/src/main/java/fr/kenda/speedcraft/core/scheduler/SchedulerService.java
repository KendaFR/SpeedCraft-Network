package fr.kenda.speedcraft.core.scheduler;

import fr.kenda.speedcraft.core.SpeedCraftCore;
import fr.kenda.speedcraft.core.utils.Logger;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class SchedulerService {

    @Getter
    private static final SchedulerService INSTANCE = new SchedulerService();
    private final HashMap<String, BukkitRunnable> schedulers = new HashMap<>();

    public void registerScheduler(String id, Scheduler scheduler) {
        schedulers.put(id, scheduler);
    }

    public void removeScheduler(String id) {
        schedulers.remove(id);
    }

    public void stopScheduler(String id) {
        try {
            schedulers.get(id).cancel();
        } catch (Exception ex) {
            Logger.error("Schduler with id " + id + " doesn't exist.");
        }
    }

    public void runScheduler(String id, long delay, long ticks) {
        try {
            schedulers.get(id).runTaskTimer(SpeedCraftCore.getCore(), delay, ticks);
        } catch (Exception ex) {
            Logger.error("Schduler with id " + id + " doesn't exist.");
        }
    }
}