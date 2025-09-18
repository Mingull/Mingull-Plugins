package nl.mingull.rankly.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import nl.mingull.rankly.commands.subcommands.StatsArgument;
import nl.mingull.rankly.configs.MessageType;
import nl.mingull.rankly.configs.Messages;
import nl.mingull.rankly.stats.StatsManager;

public class RanklyCommand implements BasicCommand {
    private static final Map<String, SubcommandExecutor> arguments = new HashMap<>();
    private static final String helpFormat = "<blue>/rankly %s <gray>- %s";

    private final Messages messages;

    public RanklyCommand(Messages messages, StatsManager statsManager) {
        this.messages = messages;

        arguments.put("stats", new StatsArgument(messages, statsManager));
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (!(source.getSender() instanceof Player player)) {
            source.getSender()
                    .sendMessage(messages.getMessage(MessageType.COMMAND_ONLY_FOR_PLAYERS));
            return;
        }

        if (args.length == 0) {
            sendHelp(player);
            return;
        }

        final String argument = args[0].toLowerCase();
        final SubcommandExecutor executor = arguments.get(argument);

        if (executor == null) {
            sendHelp(player);
            return;
        }

        executor.execute(player, args);
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args) {
        if (args.length == 1) {
            final List<String> validArguments = new ArrayList<>();
            StringUtil.copyPartialMatches(args[0], arguments.keySet(), validArguments);
            return validArguments;
        }
        if (args.length > 1) {
            final SubcommandExecutor executor = arguments.get(args[0].toLowerCase());
            if (executor == null)
                return List.of();

            if (executor instanceof SubcommandTabExecutor tabExecutor) {
                return tabExecutor.tabComplete(source.getSender(), args);
            }
        }
        return List.of();
    }

    private void sendHelp(Player player) {
        for (SubcommandExecutor executor : arguments.values()) {
            player.sendRichMessage(
                    helpFormat.formatted(executor.getUsage(), executor.getDescription()));
        }
    }
}
