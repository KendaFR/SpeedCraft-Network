package fr.kenda.speedcraft.api.database;

import fr.kenda.speedcraft.api.database.repository.RankRepository;
import fr.kenda.speedcraft.api.database.repository.UserRepository;
import fr.kenda.speedcraft.api.database.wrapper.WrapperQuery;
import lombok.Getter;

import javax.sql.DataSource;

public class Database {
    private static Database instance;
    private final DatabaseManager manager;
    @Getter
    private WrapperQuery wrapperQuery;

    @Getter
    private UserRepository userRepository;
    @Getter
    private RankRepository rankRepository;

    private Database(DatabaseCredentials credentials) {
        this.manager = new DatabaseManager(credentials);
    }

    public static void init(DatabaseCredentials credentials) {
        if (instance == null) {
            instance = new Database(credentials);
            instance.start();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Database not initialized. Call Database.init() first.");
        }
        return instance;
    }

    private void start() {
        this.manager.init();
        final DataSource source = this.manager.getDataSource();
        DatabaseMigration migration = new DatabaseMigration(source);
        migration.run();

        wrapperQuery = new WrapperQuery(source);

        userRepository = new UserRepository(source, wrapperQuery);
        rankRepository = new RankRepository(source, wrapperQuery);

        rankRepository.registerAllRanks();
    }

    public void shutdown() {
        this.manager.close();
    }

    public DatabaseManager manager() {
        return this.manager;
    }
}