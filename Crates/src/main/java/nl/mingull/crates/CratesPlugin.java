package nl.mingull.crates;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import nl.mingull.core.commandKit.CommandManager;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.commands.CreateCrateCommand;
import nl.mingull.crates.commands.EditCrateCommand;
import nl.mingull.crates.commands.ListCratesCommand;
import nl.mingull.crates.listeners.CrateLocationSelector;
import nl.mingull.crates.listeners.KeyListener;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.menus.MainMenu;

public class CratesPlugin extends JavaPlugin {
	private static CratesPlugin instance;
	private CommandManager commandManager;
	private CrateManager crateManager;
	private CrateLocationSelector crateLocationSelector;

	@Override
	public void onLoad() {
		PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
		PacketEvents.getAPI().getSettings().checkForUpdates(false).bStats(false);
		PacketEvents.getAPI().load();
	}

	@Override
	public void onEnable() {
		PacketEvents.getAPI().init();
		instance = this;
		this.saveDefaultConfig();
		this.crateManager = new CrateManager(this);
		this.crateLocationSelector = new CrateLocationSelector(crateManager);
		Bukkit.getPluginManager().registerEvents(crateLocationSelector, this);
		Bukkit.getPluginManager().registerEvents(new KeyListener(this), this);

		commandManager = new CommandManager(this, "crates", Arrays.asList("crate"));
		commandManager.setPermission(new Permission("crates.use"));
		commandManager.setOverridePermission(new Permission("crates.*"));
		commandManager
				.setPermissionMessage("<red>You do not have permission to execute this command!");
		commandManager.registerSubcommand(new ListCratesCommand());
		commandManager.registerSubcommand(new CreateCrateCommand());
		commandManager.registerSubcommand(new EditCrateCommand());
		commandManager.setExecutor(this);
	}

	@Override
	public void onDisable() {
		PacketEvents.getAPI().terminate();
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

	public CrateLocationSelector getCrateLocationSelector() {
		return crateLocationSelector;
	}
}

