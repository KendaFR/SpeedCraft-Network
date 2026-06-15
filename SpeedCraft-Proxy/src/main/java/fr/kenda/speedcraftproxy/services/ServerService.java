package fr.kenda.speedcraftproxy.services;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Volume;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.docker.DockerService;
import fr.kenda.speedcraftproxy.server.EServer;
import fr.kenda.speedcraftproxy.server.EServerType;
import fr.kenda.speedcraftproxy.server.ServerInfo;
import fr.kenda.speedcraftproxy.utils.Logger;
import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class ServerService {

    @Getter
    private static final ServerService INSTANCE = new ServerService();

    private static final List<Integer> BLOCKED_PORTS = List.of(25565);
    private static final int PORT_MIN = 25000;
    private static final int PORT_MAX = 30000;

    private final Random random = new Random();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Getter
    private final List<ServerInfo> servers = Collections.synchronizedList(new ArrayList<>());
    private DockerService dockerService;

    // --- Init / Shutdown ---

    public void init() {
        dockerService = new DockerService();
        createServer(EServer.HUB,
                info -> Logger.info("Serveur initial " + info.getName() + " prêt."),
                err  -> Logger.error("Échec du serveur initial : " + err.getMessage())
        );
    }

    public void shutdown() {
        stopAllServers();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS))
                executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // --- API publique ---

    public void createServer(EServer server, Consumer<ServerInfo> onSuccess, Consumer<Exception> onError) {
        executor.submit(() -> {
            int port = findAvailablePort();
            String name = server.getServerName().replace("xxx", generateRandomId());

            CreateContainerResponse container;
            try (var cmd = dockerService.getDockerClient()
                    .createContainerCmd(server.getEDockerImage().getImageName())
                    .withName(name)
                    .withEnv(
                            "MIN_RAM="     + server.getMinRam() + "G",
                            "MAX_RAM="     + server.getMaxRam() + "G",
                            "SERVER_PORT=" + port,
                            "SERVER_NAME=" + name
                    )
                    .withHostConfig(
                            HostConfig.newHostConfig()
                                    .withPortBindings(PortBinding.parse(port + ":" + port))
                                    .withAutoRemove(true)
                                    .withBinds(new Bind("/data/servers/" + name + "/world", new Volume("/server/world")))
                    )
            ) {
                container = cmd.exec();
            } catch (Exception e) {
                Logger.error("Échec création conteneur " + name + " : " + e.getMessage());
                onError.accept(e);
                return;
            }

            try (var cmd = dockerService.getDockerClient().startContainerCmd(container.getId())) {
                cmd.exec();
            } catch (Exception e) {
                Logger.error("Échec démarrage conteneur " + container.getId() + " : " + e.getMessage());
                onError.accept(e);
                return;
            }

            ServerInfo info = new ServerInfo(name, port, container.getId(), server.getServerType());
            servers.add(info);
            SpeedCraftProxy.getInstance().getServer().registerServer(
                    new com.velocitypowered.api.proxy.server.ServerInfo(name, new InetSocketAddress("127.0.0.1", port))
            );
            Logger.info("Serveur " + name + " démarré sur le port " + port);
            onSuccess.accept(info);
        });
    }

    public void stopServer(String serverName, Runnable onSuccess, Consumer<Exception> onError) {
        getServerById(serverName).ifPresentOrElse(
                info -> executor.submit(() -> {
                    info.setStatus(ServerInfo.ServerStatus.STOPPING);
                    try (var cmd = dockerService.getDockerClient().stopContainerCmd(info.getContainerId())) {
                        cmd.exec();
                    } catch (Exception e) {
                        Logger.error("Échec arrêt conteneur " + info.getContainerId() + " : " + e.getMessage());
                        onError.accept(e);
                        return;
                    }
                    servers.remove(info);
                    SpeedCraftProxy.getInstance().getServer().unregisterServer(
                            new com.velocitypowered.api.proxy.server.ServerInfo(info.getName(),
                                    new InetSocketAddress("127.0.0.1", info.getPort()))
                    );
                    Logger.info("Serveur " + serverName + " arrêté.");
                    onSuccess.run();
                }),
                () -> Logger.warn("stopServer : introuvable : " + serverName)
        );
    }

    public void stopAllServers() {
        new ArrayList<>(servers).forEach(info ->
                stopServer(info.getName(), () -> {}, err -> {})
        );
    }

    public Optional<ServerInfo> getServerById(String serverName) {
        return servers.stream()
                .filter(s -> s.getName().equalsIgnoreCase(serverName))
                .findFirst();
    }

    public Optional<ServerInfo> getServerByType(EServerType type) {
        return servers.stream()
                .filter(s -> s.getServerType() == type && s.getStatus() != ServerInfo.ServerStatus.STOPPING)
                .findFirst();
    }

    // --- Helpers privés ---

    private int findAvailablePort() {
        int port;
        do {
            port = random.nextInt(PORT_MIN, PORT_MAX);
            int finalPort = port;
            if (!BLOCKED_PORTS.contains(finalPort) && servers.stream().noneMatch(s -> s.getPort() == finalPort))
                return finalPort;
        } while (true);
    }

    private String generateRandomId() {
        return String.format("%05d", random.nextInt(100000));
    }
}