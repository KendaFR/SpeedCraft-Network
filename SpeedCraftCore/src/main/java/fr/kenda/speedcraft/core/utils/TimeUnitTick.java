package fr.kenda.speedcraft.core.utils;

import lombok.Getter;

public enum TimeUnitTick {

    TICKS(1),
    SECONDS(20),
    MINUTES(20 * 60),
    HOURS(20 * 60 * 60);

    @Getter
    private final long ticks;

    TimeUnitTick(long ticks) {
        this.ticks = ticks;
    }

    public static String getRemainingTimeFromTicks(long remainingTicks) {
        long hours = remainingTicks / HOURS.getTicks();
        remainingTicks %= HOURS.getTicks();

        long minutes = remainingTicks / MINUTES.getTicks();
        remainingTicks %= MINUTES.getTicks();

        long seconds = remainingTicks / SECONDS.getTicks();

        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0 || sb.isEmpty()) {
            sb.append(seconds).append("s");
        }

        return sb.toString().trim();
    }
}