package fr.kenda.speedcraft.core.scheduler;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Scheduler extends BukkitRunnable {
    protected int timer;
    protected int initTimer;

    public Scheduler(int timer) {
        this.initTimer = timer;
        this.timer = timer;
    }
}