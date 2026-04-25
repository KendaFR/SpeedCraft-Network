package fr.kenda.speedcraft.core.command;

import fr.kenda.speedcraft.core.command.annotation.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor {

    public abstract boolean onCommand(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender,
                             org.bukkit.command.Command command,
                             String label,
                             String[] args) {

        Command annotation = this.getClass().getAnnotation(Command.class);

        if (annotation != null && !annotation.consoleExecute() && !(sender instanceof Player)) {
            sender.sendMessage("§cCette commande est réservée aux joueurs.");
            return true;
        }

        return onCommand(sender, args);
    }
}