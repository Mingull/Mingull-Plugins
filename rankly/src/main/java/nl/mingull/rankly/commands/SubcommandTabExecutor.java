package nl.mingull.rankly.commands;

import java.util.Collection;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public interface SubcommandTabExecutor extends SubcommandExecutor {
    @Nullable
    public Collection<String> tabComplete(CommandSender sender, String[] args);

}
