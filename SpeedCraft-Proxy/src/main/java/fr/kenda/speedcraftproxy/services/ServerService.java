package fr.kenda.speedcraftproxy.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.docker.DockerService;
import fr.kenda.speedcraftproxy.server.EServer;
import fr.kenda.speedcraftproxy.server.EServerType;
import fr.kenda.speedcraftproxy.server.ServerInfo;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;

public class ServerService {

    @Getter
    private static final ServerService INSTANCE = new ServerService();

    private final Random random = new Random();
    private final List<Integer> BLOCKED_PORTS = List.of(25565);

    private final List<ServerInfo> servers = new ArrayList<>();
    private DockerService dockerService;

    public void init()
    {
        dockerService = new DockerService();
        createServer(EServer.HUB);
    }

    public void createServer(EServer server) {

        int randomPort;
        do {
            randomPort = random.nextInt(25000, 30000);
        } while (!isPortAvailable(randomPort));

        String serverName = server.getServerName().replace("xxx", generateRandomId());

        // Création du conteneur
        // ✅ Code complet corrigé
        CreateContainerResponse container;
        try (var createCmd = dockerService.getDockerClient()
                .createContainerCmd(server.getEDockerImage().getImageName())
                .withName(serverName)
                .withEnv(
                        "MIN_RAM=" + server.getMinRam() + "G",
                        "MAX_RAM=" + server.getMaxRam() + "G",
                        "SERVER_PORT=" + randomPort,
                        "SERVER_NAME=" + serverName
                )
                .withHostConfig(
                        HostConfig.newHostConfig()
                                .withPortBindings(PortBinding.parse(randomPort + ":" + randomPort))
                                .withAutoRemove(true)
                                .withBinds(
                                        new Bind("/data/servers/" + serverName + "/world", new Volume("/server/world"))
                                        //new Bind("/data/servers/" + serverName + "/plugins", new Volume("/server/plugins")
                                        )
                                )
                ) {
            container = createCmd.exec();
        }

        try (var startCmd = dockerService.getDockerClient().startContainerCmd(container.getId())) {
            startCmd.exec();
        }

        // ✅ Enregistrement du serveur
        servers.add(new ServerInfo(serverName, randomPort, container.getId(), server.getServerType()));
        SpeedCraftProxy.getInstance().getLogger().info("[INFO] Server " + container.getId() + " created");
        SpeedCraftProxy.getInstance().getServer().registerServer(new com.velocitypowered.api.proxy.server.ServerInfo(serverName, new InetSocketAddress("127.0.0.1", randomPort)));
    }

    private boolean isPortAvailable(int port) {
        if (BLOCKED_PORTS.contains(port)) return false;
        return servers.stream().noneMatch(s -> s.getPort() == port);
    }

    private String generateRandomId() {
        return String.valueOf(random.nextInt(99999));
    }

    public Optional<ServerInfo> getServerByType(EServerType type)
    {
        return servers.stream().filter(s -> s.getServerType() == type).findFirst();
    }
}