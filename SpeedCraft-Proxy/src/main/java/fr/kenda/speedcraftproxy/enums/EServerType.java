package fr.kenda.speedcraftproxy.enums;

public enum EServerType {
    HUB("hub_", "hub", "speedcraft-hub:latest"),
    SPEEDRUN_ITEM("speedrun_item_", "speedrun-item", "speedcraft-speedrun-item:latest"),
    SPEEDRUN_TIMER("speedrun_timer_", "speedrun-timer", "speedcraft-speedrun-timer:latest");

    private final String prefix;
    private final String templatePath;
    private final String dockerImage;

    EServerType(String prefix, String templatePath, String dockerImage) {
        this.prefix = prefix;
        this.templatePath = templatePath;
        this.dockerImage = dockerImage;
    }

    public String getPrefix() { return prefix; }
    public String getTemplatePath() { return templatePath; }
    public String getDockerImage() { return dockerImage; }
}