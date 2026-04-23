package fr.kenda.speedcraft.api.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseManager {

    private final DatabaseCredentials credentials;
    private HikariDataSource dataSource;

    public DatabaseManager(DatabaseCredentials credentials) {
        this.credentials = credentials;
    }

    public void init() {
        HikariConfig config = new HikariConfig();

        String url = "jdbc:postgresql://" +
                credentials.host() + ":" +
                credentials.port() + "/" +
                credentials.database();

        config.setJdbcUrl(url);
        config.setUsername(credentials.username());
        config.setPassword(credentials.password());

        // Supabase SSL obligatoire
        config.addDataSourceProperty("ssl", "true");
        config.addDataSourceProperty("sslmode", "require"); // "require" ou "verify-full"

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setPoolName("SpeedCraftPool");
        config.setDriverClassName("org.postgresql.Driver"); // Spécifie le driver

        this.dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}