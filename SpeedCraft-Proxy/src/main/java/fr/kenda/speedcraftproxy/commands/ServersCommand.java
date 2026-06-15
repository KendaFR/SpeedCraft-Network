package fr.kenda.speedcraftproxy.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import fr.kenda.speedcraft.api.enumeration.Rank;
import fr.kenda.speedcraftproxy.SpeedCraftProxy;
import fr.kenda.speedcraftproxy.server.EServer;
import fr.kenda.speedcraftproxy.server.EServerType;
import fr.kenda.speedcraftproxy.server.ServerInfo;
import fr.kenda.speedcraftproxy.services.ServerService;
import fr.kenda.speedcraftproxy.utils.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ServersCommand extends CommandExecutor {

    private static final String USAGE_ROOT = "Usage: /server <create|remove|list|teleport> ...";
    private static final String USAGE_CREATE = "Usage: /server create <type>";
    private static final String USAGE_REMOVE = "Usage: /server remove <server_id>";
    private static final String USAGE_TELEPORT = "Usage: /server teleport <server_id> | <player> <server_id>";

    private static final List<String> SUB_COMMANDS = List.of("create", "remove", "list", "teleport");
    private static final List<String> TYPES = EServerType.getAllTypes();

    public ServersCommand(String name, String permission) {
        super(name, permission);
    }

    @Override
    public List<String> suggestions(CommandSource source, String[] args) {

        if (args.length <= 1) {
            String filter = args.length == 1 ? args[0].toLowerCase() : "";
            return SUB_COMMANDS.stream()
                    .filter(cmd -> cmd.startsWith(filter))
                    .filter(cmd -> switch (cmd) {
                        case "create", "remove", "list" -> source.hasPermission(Rank.ADMIN.getPermission());
                        case "teleport" -> source.hasPermission(Rank.MODERATOR.getPermission());
                        default -> false;
                    })
                    .toList();
        }

        return switch (args[0].toLowerCase()) {

            // /server create <TAB>  →  types de serveur
            case "create" -> args.length == 2
                    ? TYPES.stream().filter(t -> t.startsWith(args[1].toLowerCase())).toList()
                    : List.of();

            // /server remove <TAB>  →  ids des serveurs actifs
            case "remove" -> args.length == 2
                    ? ServerService.getINSTANCE().getServers().stream()
                    .map(ServerInfo::getName)
                    .filter(n -> n.toLowerCase().startsWith(args[1].toLowerCase()))
                    .toList()
                    : List.of();

            // /server teleport <TAB>         →  joueurs en ligne ou serveurs
            // /server teleport <player> <TAB> →  serveurs
            case "teleport" -> {
                if (args.length == 2) {
                    // suggère joueurs en ligne + serveurs
                    String filter = args[1].toLowerCase();
                    List<String> players = SpeedCraftProxy.getInstance().getServer().getAllPlayers().stream()
                            .map(Player::getUsername)
                            .filter(n -> n.toLowerCase().startsWith(filter))
                            .toList();
                    List<String> servers = ServerService.getINSTANCE().getServers().stream()
                            .map(ServerInfo::getName)
                            .filter(n -> n.toLowerCase().startsWith(filter))
                            .toList();
                    yield Stream.concat(players.stream(), servers.stream()).toList();
                }
                if (args.length == 3) {
                    // deuxième arg → forcément un serveur
                    yield ServerService.getINSTANCE().getServers().stream()
                            .map(ServerInfo::getName)
                            .filter(n -> n.toLowerCase().startsWith(args[2].toLowerCase()))
                            .toList();
                }
                yield List.of();
            }

            default -> List.of();
        };
    }

    @Override
    public void execute(CommandSource source, String[] args) {
        if (args.length == 0) {
            source.sendMessage(Messages.usage(USAGE_ROOT));
            return;
        }

        ServerService service = ServerService.getINSTANCE();

        switch (args[0].toLowerCase()) {

            case "list" -> {
                if (!hasRank(source, Rank.ADMIN)) return;
                var servers = service.getServers();
                if (servers.isEmpty()) {
                    source.sendMessage(Messages.info("Aucun serveur actif."));
                    return;
                }
                source.sendMessage(Messages.info("── Serveurs actifs (" + servers.size() + ") ──"));
                servers.forEach(s -> source.sendMessage(
                        Component.text("  • ", NamedTextColor.GRAY)
                                .append(Component.text(s.getName(), NamedTextColor.AQUA))
                                .append(Component.text(" [" + s.getServerType().name() + "]", NamedTextColor.GRAY))
                                .append(Component.text(" ⏱ " + s.getUptime(), NamedTextColor.YELLOW))
                                .append(Component.text(" — " + s.getStatus().name(), Messages.statusColor(s.getStatus())))
                ));
            }

            case "create" -> {
                if (!hasRank(source, Rank.ADMIN)) return;
                if (args.length != 2) {
                    source.sendMessage(Messages.usage(USAGE_CREATE));
                    return;
                }
                switch (args[1].toLowerCase()) {
                    case "hub" -> {
                        source.sendMessage(Messages.info("Création du serveur HUB en cours..."));
                        service.createServer(EServer.HUB,
                                created -> source.sendMessage(Messages.success(
                                        "Serveur " + created.getName() + " démarré sur le port " + created.getPort() + ".")),
                                err     -> source.sendMessage(Messages.error(
                                        "Échec de la création : " + err.getMessage()))
                        );
                    }
                    default -> source.sendMessage(Messages.error("Type inconnu. Types disponibles : HUB"));
                }
            }

            case "remove" -> {
                if (!hasRank(source, Rank.ADMIN)) return;
                if (args.length != 2) {
                    source.sendMessage(Messages.usage(USAGE_REMOVE));
                    return;
                }
                String serverId = args[1];
                service.getServerById(serverId).ifPresentOrElse(
                        __ -> {
                            source.sendMessage(Messages.info("Arrêt du serveur " + serverId + "..."));
                            service.stopServer(serverId,
                                    ()  -> source.sendMessage(Messages.success("Serveur " + serverId + " arrêté.")),
                                    err -> source.sendMessage(Messages.error("Échec : " + err.getMessage()))
                            );
                        },
                        () -> source.sendMessage(Messages.error("Aucun serveur trouvé : " + serverId))
                );
            }

            case "teleport" -> {
                if (!hasRank(source, Rank.MODERATOR)) return;
                if (args.length < 2 || args.length > 3) {
                    source.sendMessage(Messages.usage(USAGE_TELEPORT));
                    return;
                }
                if (args.length == 3) teleportPlayer(source, args[1], args[2]);
                else if (source instanceof Player player) teleportPlayerObject(source, player, args[1], true);
                else source.sendMessage(Messages.error("La console doit spécifier un joueur cible."));
            }

            default -> source.sendMessage(Messages.usage(USAGE_ROOT));
        }
    }

    // --- Permission ---

    private boolean hasRank(CommandSource source, Rank rank) {
        if (source.hasPermission(rank.getPermission())) return true;
        source.sendMessage(Messages.error("Vous n'avez pas la permission d'utiliser cette commande."));
        return false;
    }

    // --- Téléportation ---

    private void teleportPlayer(CommandSource source, String playerName, String serverId) {
        SpeedCraftProxy.getInstance().getServer().getPlayer(playerName).ifPresentOrElse(
                player -> teleportPlayerObject(source, player, serverId, false),
                () -> source.sendMessage(Messages.error("Joueur introuvable : " + playerName))
        );
    }

    private void teleportPlayerObject(CommandSource source, Player player, String serverId, boolean isSelf) {
        ServerService.getINSTANCE().getServerById(serverId).ifPresentOrElse(
                serverInfo -> SpeedCraftProxy.getInstance().getServer()
                        .getServer(serverInfo.getName())
                        .ifPresentOrElse(
                                reg -> {
                                    player.createConnectionRequest(reg).fireAndForget();
                                    if (!isSelf)
                                        player.sendMessage(Messages.info("Vous avez été téléporté sur " + serverId + " par un administrateur."));
                                    source.sendMessage(isSelf
                                            ? Messages.success("Téléportation vers " + serverId + " en cours...")
                                            : Messages.success("Téléportation de " + player.getUsername() + " vers " + serverId + " effectuée."));
                                },
                                () -> source.sendMessage(Messages.error("Serveur non enregistré dans Velocity : " + serverInfo.getName()))
                        ),
                () -> source.sendMessage(Messages.error("Aucun serveur trouvé avec l'id : " + serverId))
        );
    }
}