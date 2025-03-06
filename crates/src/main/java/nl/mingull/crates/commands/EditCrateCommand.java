package nl.mingull.crates.commands;

import java.util.Collections;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class EditCrateCommand extends Subcommand {
	private final CratesPlugin plugin;

	public EditCrateCommand() {
		super("edit", "Edit a crate");
		addPermission(new Permission("crates.edit"));
		this.plugin = CratesPlugin.getInstance();
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
		if (args.length < 3) {
			sender.sendMessage(Messenger.format(
					"<blue>Usage: /crates edit <crate_name> <name|displayName|addLocation|removeLocation|addReward|removeReward> <value>"));
			return false;
		}

		String crateName = args[0];
		Crate crate = plugin.getCrateManager().getCrateByName(crateName);
		if (crate == null) {
			sender.sendMessage(Messenger.format("<red>Crate not found."));
			return false;
		}

		String action = args[1];

		switch (action.toLowerCase()) {
			case "displayname" -> {
				crate.setDisplayName(args[2]);
				sender.sendMessage("§aCrate display name updated.");
			}
			case "addlocation", "removelocation" -> {
				if (args.length < 6) {
					sender.sendMessage("§cUsage: /crates edit <crate> " + action + " <x> <y> <z>");
					return false;
				}
				try {
					double x = Double.parseDouble(args[2]);
					double y = Double.parseDouble(args[3]);
					double z = Double.parseDouble(args[4]);
					Location loc = new Location(null, x, y, z);

					if (action.equalsIgnoreCase("addlocation")) {
						crate.addLocation(loc);
						sender.sendMessage("§aAdded location: " + x + ", " + y + ", " + z);
					} else {
						crate.removeLocation(loc);
						sender.sendMessage("§aRemoved location: " + x + ", " + y + ", " + z);
					}
				} catch (NumberFormatException e) {
					sender.sendMessage("§cInvalid coordinates.");
				}
			}
			case "addreward" -> {
				CrateReward reward = new CrateReward(Material.getMaterial(args[2]),
						Integer.parseInt(args[3]), Integer.parseInt(args[4]));
				crate.addReward(reward);
				sender.sendMessage("§aAdded reward: " + reward);
			}
			case "removereward" -> {
				// crate.removeReward(reward);
				// sender.sendMessage("§aRemoved reward: " + reward);

			}
			default -> sender.sendMessage("§cUnknown edit action.");
		}

		return true;
	}

	@Override
	public @Nullable List<String> onTab(@NotNull CommandSender sender, @NotNull String[] args) {
		if (args.length == 1) {
			return plugin.getCrateManager().getCrates().stream().map(Crate::getName).toList();
		} else if (args.length == 2) {
			return List.of("displayName", "addLocation", "removeLocation", "addReward",
					"removeReward");
		} else if (args.length == 3) {
			Crate crate = plugin.getCrateManager().getCrateByName(args[0]);
			if (crate == null) {
				return Collections.emptyList();
			}
			switch (args[1].toLowerCase()) {
				case "displayname" -> {
					return List.of(crate.getDisplayName());
				}
				case "addlocation", "removelocation" -> {
					return List.of("<x> <y> <z>");
				}
				case "addreward" -> {
					return List.of("<material>");
				}
				case "removereward" -> {
					return crate.getRewards().stream().map(CrateReward::getMaterial)
							.map(Material::name).toList();
				}
			}
		} else if (args.length == 4) {
			if (args[1].equalsIgnoreCase("addLocation")
					|| args[1].equalsIgnoreCase("removeLocation")) {
				return List.of("<y>");
			} else if (args[1].equalsIgnoreCase("addReward")) {
				return List.of("<amount>");
			}
		} else if (args.length == 5) {
			if (args[1].equalsIgnoreCase("addLocation")
					|| args[1].equalsIgnoreCase("removeLocation")) {
				return List.of("<z>");
			} else if (args[1].equalsIgnoreCase("addReward")) {
				return List.of("<weight>");
			}
		}

		return Collections.emptyList();
	}


}
