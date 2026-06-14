package fr.kenda.speedcraftproxy.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.command.CommandSource;
import lombok.Getter;

@Getter
public abstract class CommandExecutor implements SimpleCommand {

    private final String name;
    private final String permission;

    public CommandExecutor(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return permission == null || invocation.source().hasPermission(permission);
    }

    @Override
    public void execute(Invocation invocation) {
        execute(invocation.source(), invocation.arguments());
    }

    public abstract void execute(CommandSource source, String[] args);
}