package fr.kenda.speedcraft.api.database;

public record DatabaseCredentials(
        String host,
        int port,
        String database,
        String username,
        String password
) {
}