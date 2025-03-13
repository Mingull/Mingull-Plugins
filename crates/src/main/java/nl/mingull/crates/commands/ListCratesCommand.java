package nl.mingull.crates.commands;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.models.Crate;

public class ListCratesCommand extends Subcommand {
	private final CratesPlugin plugin;

	public ListCratesCommand() {
		super("list", "List all available crates");
		addPermission(new Permission("crates.list"));
		this.plugin = CratesPlugin.get();
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
		List<Crate> crates = plugin.getManager(CrateManager.class).getCrates();

		if (crates.isEmpty()) {
			sender.sendMessage(Messenger.format("<red>There are no crates available."));
			return true;
		}

		sender.sendMessage(Messenger.format("<gold><bold>Available Crates:</bold></gold>"));
		for (Crate crate : crates) {
			sender.sendMessage(Messenger
					.format("<yellow> - <aqua>" + crate.getDisplayName() + "</aqua></yellow>"));
		}
		return true;
	}
}
