package fr.kenda.speedcraft.core.utils;

import lombok.Getter;

public enum TimeUnitMillis {

    MILLISECONDS(1),
    SECONDS(1000),
    MINUTES(1000 * 60),
    HOURS(1000 * 60 * 60);

    @Getter
    private final long millis;

    TimeUnitMillis(long millis) {
        this.millis = millis;
    }

    public static String getRemainingTimeFromMillis(long remainingMillis) {
        long hours = remainingMillis / HOURS.getMillis();
        remainingMillis %= HOURS.getMillis();

        long minutes = remainingMillis / MINUTES.getMillis();
        remainingMillis %= MINUTES.getMillis();

        long seconds = remainingMillis / SECONDS.getMillis();

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