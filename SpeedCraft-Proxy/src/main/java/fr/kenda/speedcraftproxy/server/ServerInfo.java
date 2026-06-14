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

    public ServerInfo(String name, int port, String containerId, EServerType type) {
        this.name = name;
        this.port = port;
        this.status = ServerStatus.STARTING;
        this.containerId = containerId;
        this.serverType = type;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
        System.out.println("[" + name + "] Status → " + status);
    }

    @Override
    public String toString() {
        return "Name: " + name + "/Port: " + port + "/Status: " + status.toString();
    }
}