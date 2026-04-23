package fr.kenda.speecraft.api.database.wrapper;

import javax.sql.DataSource;
import java.sql.*;

public class WrapperQuery {

    private static DataSource dataSource;

    public WrapperQuery(DataSource source) {
        dataSource = source;
    }

    public <T> T executeQuery(String sql,
                              SQLConsumer<PreparedStatement> setter,
                              SQLFunction<ResultSet, T> mapper) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            setter.accept(stmt);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapper.apply(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int executeUpdate(String sql,
                             SQLConsumer<PreparedStatement> setter) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            setter.accept(stmt);
            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int executeInsert(String sql,
                             SQLConsumer<PreparedStatement> setter) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setter.accept(stmt);
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }

            return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int delete(String sql, Object value) {
        return executeUpdate(sql, stmt -> stmt.setObject(1, value));
    }

    public void executeQuery(String sql) {
        executeUpdate(sql, stmt -> {
        });
    }
}