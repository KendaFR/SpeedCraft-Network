package fr.kenda.speedcraft.core.scoreboard;

import fr.kenda.speedcraft.core.fastboard.FastBoard;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class ScoreboardService {

    @Getter
    private static final ScoreboardService INSTANCE = new ScoreboardService();

    private final HashMap<UUID, FastBoard> scoreboards = new HashMap<>();

    public FastBoard createBoard(Player player) {
        FastBoard board = new FastBoard(player);
        scoreboards.put(player.getUniqueId(), board);
        return board;
    }
    public void removeBoard(Player player) {
        scoreboards.remove(player.getUniqueId());
    }

    public void clearBoard() {
        scoreboards.forEach((uuid, fastBoard) -> fastBoard.delete());
        scoreboards.clear();
    }
}