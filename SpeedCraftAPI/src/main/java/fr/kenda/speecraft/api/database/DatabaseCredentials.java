package fr.kenda.speecraft.api.database;

public record DatabaseCredentials(
        String host,
        int port,
        String database,
        String username,
        String password
) {
}