package nl.mingull.rankly.commands.subcommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.rankly.commands.SubcommandTabExecutor;
import nl.mingull.rankly.configs.MessageType;
import nl.mingull.rankly.configs.Messages;
import nl.mingull.rankly.stats.PlayerStats;

public class StatsArgument implements SubcommandTabExecutor {
    private final Messages messages;
    private final Map<UUID, PlayerStats> statsMap = new HashMap<>();

    public StatsArgument(Messages messages) {
        this.messages = messages;

        for (Player player : Bukkit.getOnlinePlayers()) {
            statsMap.put(player.getUniqueId(),
                    new PlayerStats(player.getName(), 0, player.getStatistic(Statistic.PLAYER_KILLS),
                            player.getStatistic(Statistic.DEATHS),
                            player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20));
        }
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

        PlayerStats stats = statsMap.get(target.getUniqueId());
        player.sendMessage(
                messages.getMessage(MessageType.PLAYER_STATS, Placeholder.unparsed("player", target.getName()),
                        Placeholder.unparsed("kills", String.valueOf(stats.getKills())),
                        Placeholder.unparsed("deaths", String.valueOf(stats.getDeaths())),
                        Placeholder.unparsed("joins", String.valueOf(stats.getJoins())),
                        Placeholder.unparsed("playtime", String.valueOf(stats.getPlaytime()))));
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
