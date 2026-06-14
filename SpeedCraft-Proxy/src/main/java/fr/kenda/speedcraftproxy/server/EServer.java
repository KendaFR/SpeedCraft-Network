package fr.kenda.speedcraftproxy.server;

import fr.kenda.speedcraftproxy.docker.EDockerImage;
import lombok.Getter;

public enum EServer {

    HUB("lobby_xxx", 2, 4, EDockerImage.HUB, EServerType.HUB);


    @Getter
    private final String serverName;
    @Getter
    private final int minRam;
    @Getter
    private final int maxRam;
    @Getter
    private final EDockerImage eDockerImage;
    @Getter
    private final EServerType serverType;

    EServer(String lobbyName, int minRam, int maxRam, EDockerImage eDockerImage, EServerType serverType) {
    serverName = lobbyName;
    this.minRam = minRam;
    this.maxRam = maxRam;
    this.eDockerImage = eDockerImage;
    this.serverType = serverType;
    }
}
