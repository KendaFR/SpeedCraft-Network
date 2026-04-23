package fr.kenda.speedcraft.api.database.repository;

import fr.kenda.speedcraft.api.database.Database;
import fr.kenda.speedcraft.api.database.wrapper.WrapperQuery;
import fr.kenda.speedcraft.api.enumeration.Rank;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RankRepository extends Repository {


    public RankRepository(DataSource source, WrapperQuery wrapperQuery) {
        super(source, wrapperQuery);
    }

    public int findIdRankByName(String rankName) {
        String sql = "SELECT id FROM rank WHERE name = ?";

        Integer id = wrapperQuery.executeQuery(
                sql,
                stmt -> stmt.setString(1, rankName),
                rs -> rs.getInt(1)
        );
        return id == null ? -1 : id;
    }

    public boolean rankAlreadyExist(Rank rank) {
        String sql = "SELECT * FROM rank WHERE name = ? AND power = ?";

        Boolean result = wrapperQuery.executeQuery(sql, stmt ->
                {
                    stmt.setString(1, rank.name());
                    stmt.setInt(2, rank.getPower());
                },
                ResultSet::next);

        return result != null;
    }

    public void registerAllRanks() {
        List<Rank> rankNotRegister = new ArrayList<>();
        for (Rank value : Rank.values()) {
            if (!Database.getInstance().getRankRepository().rankAlreadyExist(value)) {
                rankNotRegister.add(value);
            }
        }

        String query = "INSERT INTO rank (name, power) VALUES ";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rankNotRegister.size(); i++) {
            Rank r = rankNotRegister.get(i);
            sb.append("('")
                    .append(r.getNameRank())
                    .append("',")
                    .append(r.getPower())
                    .append(")");
            if (i + 1 != rankNotRegister.size()) sb.append(",");
        }
        String sql = query + sb;

        wrapperQuery.executeQuery(sql);
    }
}