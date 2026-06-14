package fr.kenda.speedcraftproxy.docker;

import lombok.Getter;

public enum EDockerImage {

    HUB("hub");

    @Getter
    private final String imageName;

    EDockerImage(String imageName) {
        this.imageName = imageName;
    }
}