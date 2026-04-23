package fr.kenda.speedcraft.api.enumeration;

public enum EExtension {
    YAML("yaml"), YML("yml"), SETTINGS("settings");

    public final String extension;

    EExtension(String extension) {
        this.extension = extension;
    }
}