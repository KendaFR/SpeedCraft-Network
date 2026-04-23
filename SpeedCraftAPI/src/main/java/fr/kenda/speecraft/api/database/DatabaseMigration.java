package fr.kenda.speecraft.api.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseMigration {

    private final DataSource dataSource;

    public DatabaseMigration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void run() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(getRunModeTable());
            stmt.execute(getRankTable());
            stmt.execute(getUserTable());
            stmt.execute(getRunTable());

            System.out.println("[DB] Migrations OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRunModeTable() {
        return """
                    CREATE TABLE IF NOT EXISTS run_mode (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name VARCHAR(50) NOT NULL
                    );
                """;
    }

    private String getRankTable() {
        return """
                    CREATE TABLE IF NOT EXISTS rank (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      power INT NOT NULL
                    );
                """;
    }

    private String getUserTable() {
        return """
                    CREATE TABLE IF NOT EXISTS profile (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      player_name VARCHAR(50) NOT NULL,
                      last_connection TIMESTAMP NOT NULL,
                      first_connection TIMESTAMP NOT NULL,
                      uuid VARCHAR(36) NOT NULL,
                      id_rank INT NOT NULL,
                      FOREIGN KEY (id_rank) REFERENCES rank(id)
                    );
                """;
    }

    private String getRunTable() {
        return """
                    CREATE TABLE IF NOT EXISTS run (
                      id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                      time_ms INT NOT NULL,
                      date TIMESTAMP NOT NULL,
                      id_run_mode INT NOT NULL,
                      id_user INT NOT NULL,
                      FOREIGN KEY (id_run_mode) REFERENCES run_mode(id),
                      FOREIGN KEY (id_user) REFERENCES profile(id)
                    );
                """;
    }
}