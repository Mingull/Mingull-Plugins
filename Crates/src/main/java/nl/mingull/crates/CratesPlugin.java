package nl.mingull.crates;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import nl.mingull.core.commandKit.CommandManager;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.commands.CreateCommand;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.menus.MainMenu;

public class CratesPlugin extends JavaPlugin implements Listener {
	private static CratesPlugin instance;
	private CommandManager commandManager;
	private CrateManager crateManager;

	@Override
	public void onLoad() {}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		instance = this;
		this.saveDefaultConfig();
		crateManager = new CrateManager(this);

		commandManager = new CommandManager(this, "crates", Arrays.asList("crate"));
		commandManager.setPermission(new Permission("crates.use"));
		commandManager.setOverridePermission(new Permission("crates.*"));
		commandManager
				.setPermissionMessage("<red>You do not have permission to execute this command!");
		commandManager.registerSubcommand(new CreateCommand(crateManager));
		commandManager.setExecutor(this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
			@NotNull String label, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(
					Messenger.format("<red>This command can only be executed by players!"));
			return true;
		}

		try {
			MenuManager.openMenu(MainMenu.class, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static CratesPlugin getInstance() {
		return instance;
	}

	/**
	 * @return the commandManager
	 */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * @return the crateManager
	 */
	public CrateManager getCrateManager() {
		return crateManager;
	}
}

