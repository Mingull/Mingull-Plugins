package nl.mingull.rankly.commands;

import org.bukkit.entity.Player;

public interface SubcommandExecutor {
    void execute(Player player, String[] args);

    String getUsage();

    String getDescription();
}
