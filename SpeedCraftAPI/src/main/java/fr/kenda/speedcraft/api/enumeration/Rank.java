package fr.kenda.speedcraft.api.enumeration;

import lombok.Getter;

public enum Rank {
    PLAYER("Joueur", 0, "§7", "speedcraft.player", "§cVous n'avez pas la permission de faire cette commande. [>Joueur]"),
    MODERATOR("Modérateur", 500, "§3", "speedcraft.moderator", "§cVous n'avez pas la permission de faire cette commande. [>Moderateur]"),
    ADMIN("Admin", 999, "§c", "speedcraft.admin", "§cVous n'avez pas la permission de faire cette commande. [>Admin]");

    @Getter
    private final String nameRank;
    @Getter
    private final int power;
    @Getter
    private final String chatColor;
    @Getter
    private final String permission;
    @Getter
    private final String noPermissionMessage;

    Rank(String nameRank, int power, String chatColor, String permission, String noPermissionMessage) {
        this.nameRank = nameRank;
        this.power = power;
        this.chatColor = chatColor;
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage;
    }
}