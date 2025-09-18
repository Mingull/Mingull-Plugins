package nl.mingull.rankly.commands.subcommands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.rankly.commands.SubcommandTabExecutor;
import nl.mingull.rankly.configs.MessageType;
import nl.mingull.rankly.configs.Messages;
import nl.mingull.rankly.stats.PlayerStats;
import nl.mingull.rankly.stats.StatsManager;

public class StatsArgument implements SubcommandTabExecutor {
    private final Messages messages;
    private final StatsManager statsManager;

    public StatsArgument(Messages messages, StatsManager statsManager) {
        this.messages = messages;
        this.statsManager = statsManager;
    }

    @Override
    public void execute(Player player, String[] args) {
        Player target;
        if (args.length < 2) {
            target = player;
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("Player not found!");
                return;
            }
        }

        final java.util.Optional<PlayerStats> optionalStats = statsManager.getPlayerStats(target.getName());
        if (!optionalStats.isPresent()) {
            player.sendMessage("Stats not found for player: " + target.getName());
            return;
        }
        final PlayerStats stats = optionalStats.get();

        player.sendMessage(
                messages.getMessage(MessageType.PLAYER_STATS, Placeholder.unparsed("player", target.getName()),
                        Placeholder.unparsed("kills", String.valueOf(stats.getKills().getValue())),
                        Placeholder.unparsed("deaths", String.valueOf(stats.getDeaths().getValue())),
                        Placeholder.unparsed("joins", String.valueOf(stats.getJoins().getValue())),
                        Placeholder.unparsed("playtime", String.valueOf(stats.getPlaytime().getValue()))));
    }

    @Override
    public String getUsage() {
        return "stats [player]";
    }

    @Override
    public String getDescription() {
        return "Displays your or a player's game statistics.";
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }
}
