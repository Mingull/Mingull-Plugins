package nl.mingull.multibank.commands;

import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.multibank.MultiBankPlugin;

public class ReloadCommand extends Subcommand {
	public ReloadCommand() {
		super("reload", "Reload the plugin configuration");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		// Reload the plugin configuration
		MultiBankPlugin.getInstance().reloadConfig();
		sender.sendMessage(Component.text("Configuration reloaded!").color(NamedTextColor.GREEN));
		return true;
	}

}
