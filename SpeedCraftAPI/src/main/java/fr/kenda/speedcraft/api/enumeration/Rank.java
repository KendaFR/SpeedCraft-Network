package fr.kenda.speedcraft.api.enumeration;

import lombok.Getter;

public enum Rank {
    PLAYER("Joueur", 0, "§7"),
    MODERATOR("Modérateur", 500, "§3"),
    ADMIN("Admin", 999, "§c");

    @Getter
    private final String nameRank;
    @Getter
    private final int power;
    @Getter
    private final String chatColor;

    Rank(String nameRank, int power, String chatColor) {
        this.nameRank = nameRank;
        this.power = power;
        this.chatColor = chatColor;
    }
}