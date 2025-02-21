package nl.mingull.coreTest.commands;

import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.coreTest.CoreTestPlugin;

public class ReloadCommand extends Subcommand {
	public ReloadCommand() {
		super("reload", "Reload the plugin configuration", "/ref reload");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		// Reload the plugin configuration
		CoreTestPlugin.getInstance().reloadConfig();
		sender.sendMessage(Component.text("Configuration reloaded!").color(NamedTextColor.GREEN));
		return true;
	}

}
