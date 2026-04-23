package fr.kenda.speecraft.api.database.repository;

import fr.kenda.speecraft.api.database.Database;
import fr.kenda.speecraft.api.database.data.UserProfile;
import fr.kenda.speecraft.api.database.wrapper.WrapperQuery;

import javax.sql.DataSource;

public class UserRepository extends Repository {

    public UserRepository(DataSource source, WrapperQuery wrapperQuery) {
        super(source, wrapperQuery);
    }

    public int createProfile(UserProfile profile)
    {
        String sql = "INSERT INTO profile(player_name, last_connection, first_connection, uuid, id_rank) " +
                "VALUES(?, now(), now(), ?, ?);";

        return wrapperQuery.executeInsert(sql, stmt ->
        {
           stmt.setString(1, profile.playerName());
           stmt.setString(2, profile.uuid().toString());
           stmt.setInt(3, Database.getInstance().getRankRepository().findIdRankByName(profile.rank().getNameRank()));
        });
    }
}
