package nl.mingull.skywars.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.mingull.skywars.commands.arguments.CreateGameArgument;
import nl.mingull.skywars.commands.arguments.JoinGameArgument;
import nl.mingull.skywars.commands.arguments.SetTeamSpawnArgument;
import nl.mingull.skywars.configs.MessageType;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.game.GameManager;

public class SkywarsCommand implements TabExecutor {
    private static final Map<String, ArgumentExecutor> arguments = new HashMap<>();
    private static final String helpFormat = "<blue>/skywars %s <gray>- %s";

    private final GameManager manager;
    private final Messages messages;

    public SkywarsCommand(GameManager manager, Messages messages) {
        this.manager = manager;
        this.messages = messages;

        arguments.put("create", new CreateGameArgument(messages, manager));
        arguments.put("spawn", new SetTeamSpawnArgument(messages, manager));
        arguments.put("join", new JoinGameArgument(messages, manager));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s,
            @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messages.getMessage(MessageType.COMMAND_ONLY_FOR_PLAYERS));
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        final String argument = args[0].toLowerCase();
        final ArgumentExecutor executor = arguments.get(argument);

        if (executor == null) {
            sendHelp(player);
            return true;
        }

        executor.execute(player, args);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
            @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            final List<String> validArguments = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], arguments.keySet(), validArguments);
            return validArguments;
        }

        return List.of();
    }

    private void sendHelp(Player player) {
        for (ArgumentExecutor executor : arguments.values()) {
            player.sendRichMessage(
                    helpFormat.formatted(executor.getUsage(), executor.getDescription()));
        }
    }
}
