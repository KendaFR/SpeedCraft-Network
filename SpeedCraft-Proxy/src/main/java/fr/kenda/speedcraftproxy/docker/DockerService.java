package fr.kenda.speedcraftproxy.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import fr.kenda.speedcraftproxy.services.ServerService;
import lombok.Getter;

public class DockerService {

    @Getter
    public static final ServerService INSTANCE = new ServerService();

    @Getter
    private DockerClient dockerClient;

    public DockerService()
    {
        // Détection OS pour le bon socket
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("win");
        String dockerHost = isWindows ? "tcp://localhost:2375" : "unix:///var/run/docker.sock";

        // Connexion à Docker
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost)
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }
}
