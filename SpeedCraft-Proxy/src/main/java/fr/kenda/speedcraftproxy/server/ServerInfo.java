package fr.kenda.speedcraftproxy.server;

import lombok.Getter;

@Getter
public class ServerInfo {


    public enum ServerStatus {
        STARTING,
        RUNNING,
        STOPPING
    }

    @Getter
    private final String name;
    @Getter
    private final int port;
    @Getter
    private ServerStatus status;
    @Getter
    private String containerId;
    @Getter
    private EServerType serverType;

    private final long startedAt;

    public ServerInfo(String name, int port, String containerId, EServerType type) {
        this.name = name;
        this.port = port;
        this.status = ServerStatus.STARTING;
        this.containerId = containerId;
        this.serverType = type;
        this.startedAt = System.currentTimeMillis();
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
        System.out.println("[" + name + "] Status → " + status);
    }

    @Override
    public String toString() {
        return "Name: " + name + "/Port: " + port + "/Status: " + status.toString();
    }

    public String getUptime() {
        long elapsed = System.currentTimeMillis() - startedAt;

        long hours   = elapsed / 3_600_000;
        long minutes = (elapsed % 3_600_000) / 60_000;
        long seconds = (elapsed % 60_000) / 1_000;

        if (hours > 0)   return String.format("%dh %02dm %02ds", hours, minutes, seconds);
        if (minutes > 0) return String.format("%dm %02ds", minutes, seconds);
        return String.format("%ds", seconds);
    }
}