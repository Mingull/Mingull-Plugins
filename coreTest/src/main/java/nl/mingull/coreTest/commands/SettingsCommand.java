package nl.mingull.coreTest.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.mingull.core.Utils;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.core.configKit.ConfigManager;
import nl.mingull.core.menuKit_old.AdvancedIcon;
import nl.mingull.core.menuKit_old.Rows;
import nl.mingull.core.menuKit_old.SimpleMenu;
import nl.mingull.core.utils.Messenger;
import nl.mingull.coreTest.CoreTestPlugin;

public class SettingsCommand extends Subcommand {
	private final SimpleMenu menu;
	private ConfigManager configManager;

	public SettingsCommand() {
		super("settings", "Open the settings menu");
		configManager = CoreTestPlugin.getConfigManager();
		addPermission(Utils.Permissions.SETTINGS);

		this.menu = new SimpleMenu(Rows.THREE, Component.text("Settings", NamedTextColor.GRAY));

		// Fill empty slots with a glass pane
		// this.menu.fill(Icons.GlassPane.StainedGray);

		// Load and set the initial debug mode icon
		var config = configManager.getConfig("config");
		boolean isDebugEnabled = config.getBoolean("debugger");
		updateDebugIcon(isDebugEnabled);
	}

	private void updateDebugIcon(boolean isDebugEnabled) {
		menu.setIcon(13, createDebugModeIcon(isDebugEnabled));
	}

	private AdvancedIcon createDebugModeIcon(boolean isDebugEnabled) {
		AdvancedIcon icon = new AdvancedIcon(Material.REDSTONE, Messenger.format("<red>Debug mode</red>"));
		icon.setLore(
				Messenger.format("<gray>Setting: "
						+ (isDebugEnabled ? "<green>Enabled" : "<red>Disabled")),
				Component.empty(), Messenger.format("<gray>Debug mode is used to display"),
				Messenger.format("<gray>extra information in the console."));
		icon.setAction(this::toggleDebugMode);
		return icon;
	}

	private void toggleDebugMode(Player player) {
		var configManager = CoreTestPlugin.getConfigManager();
		var settings = configManager.getConfig("config");

		// Toggle and save new state
		boolean newState = !settings.getBoolean("debugger");
		settings.set("debugger", newState);
		configManager.saveConfig("config");

		// Notify player
		player.sendMessage(Component.text("Debug mode has been ", NamedTextColor.GRAY)
				.append(Component.text(newState ? "enabled" : "disabled",
						newState ? NamedTextColor.GREEN : NamedTextColor.RED))
				.append(Component.text("!")));

		// Update the icon and refresh the menu
		updateDebugIcon(newState);
		menu.open(player);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(
					Component.text("Only players can use this command!", NamedTextColor.RED));
			return true;
		}
		menu.open(player);
		return true;
	}
}
