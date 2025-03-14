package nl.mingull.crates.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;

public class CreateCrateCommand extends Subcommand {
	private final CratesPlugin plugin;

	public CreateCrateCommand() {
		super("create", "Create a new crate");
		addPermission(new Permission("crates.create"));
		this.plugin = CratesPlugin.get();
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(
					Messenger.format("<red>You must be a player to execute this command!"));
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(Messenger.format("<red>Usage: /crate create <name>"));
			return true;
		}

		String crateName = args[0];
		if (plugin.getManager(CrateManager.class).crateExists(crateName)) {
			player.sendMessage(Messenger.format("<red>A crate with that name already exists!"));
			return true;
		}

		plugin.getManager(CrateManager.class).createCrate(crateName);
		player.sendMessage(
				Messenger.format("<green>Successfully created crate <gold>" + crateName));

		return true;
	}

}
