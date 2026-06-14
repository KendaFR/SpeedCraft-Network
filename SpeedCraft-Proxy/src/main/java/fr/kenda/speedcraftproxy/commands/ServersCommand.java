package fr.kenda.speedcraftproxy.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.services.ServerService;
import net.kyori.adventure.text.Component;

import java.util.Optional;
import java.util.logging.Logger;

public class ServersCommand extends CommandExecutor {

    public ServersCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public void execute(CommandSource source, String[] args) {
        if (args.length == 0) {
            SpeedCraftProxy.getInstance()
                    .getServer().getAllServers().forEach(registeredServer ->
                    System.out.println(registeredServer.getServerInfo().getName() + " " + registeredServer.getServerInfo().getAddress()));
        } else
            SpeedCraftProxy.getInstance().getServer().sendMessage(Component.text("test X args"));
    }
}