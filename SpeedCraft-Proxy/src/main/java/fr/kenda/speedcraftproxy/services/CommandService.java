package fr.kenda.speedcraftproxy.services;

import com.velocitypowered.api.command.CommandManager;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.commands.CommandExecutor;
import fr.kenda.speedcraftproxy.commands.ServersCommand;
import lombok.Getter;

import java.util.Set;

public class CommandService {

    @Getter
    private static final CommandService INSTANCE = new CommandService();

    public void registerCommands() {
        CommandManager manager = SpeedCraftProxy.getInstance().getServer().getCommandManager();
        Set<CommandExecutor> cmd = Set.of(
                new ServersCommand("servers", null)
        );

        cmd.forEach(commandInfo ->
                manager.register(
                        manager.metaBuilder(commandInfo.getName()).build(),
                        commandInfo
                ));
    }
}