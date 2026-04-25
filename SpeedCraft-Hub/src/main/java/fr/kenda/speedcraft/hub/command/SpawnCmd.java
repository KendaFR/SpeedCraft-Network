package fr.kenda.speedcraft.hub.command;

import fr.kenda.speedcraft.core.command.AbstractCommand;
import fr.kenda.speedcraft.core.command.annotation.Command;
import fr.kenda.speedcraft.core.utils.FileConfig;
import fr.kenda.speedcraft.hub.utils.FileConfigurationUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "spawn", consoleExecute = false)
public class SpawnCmd extends AbstractCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;
        FileConfig config = FileConfigurationUtils.HUB_PROPERTIES;

        player.teleport(new Location(
                player.getWorld(),
                config.getOrDefault("spawn.x", 0.0, Double.class),
                config.getOrDefault("spawn.y", 0.0, Double.class),
                config.getOrDefault("spawn.z", 0.0, Double.class),
                config.getOrDefault("spawn.yaw", 0.0, Double.class).floatValue(),
                config.getOrDefault("spawn.pitch", 180.0, Double.class).floatValue()
        ));
        return false;
    }
}