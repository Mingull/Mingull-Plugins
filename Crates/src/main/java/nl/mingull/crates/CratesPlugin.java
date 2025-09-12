package nl.mingull.crates;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import nl.mingull.core.chatKit.ChatManager;
import nl.mingull.core.commandKit.CommandManager;
import nl.mingull.core.configKit.Config;
import nl.mingull.core.configKit.ConfigManager;
import nl.mingull.core.managerKit.Manager;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.commands.CreateCrateCommand;
import nl.mingull.crates.commands.EditCrateCommand;
import nl.mingull.crates.commands.ListCratesCommand;
import nl.mingull.crates.configs.DefaultConfig;
import nl.mingull.crates.listeners.CrateListener;
import nl.mingull.crates.managers.CrateSelectionManager;
import nl.mingull.crates.managers.HologramManager;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.managers.KeyManager;
import nl.mingull.crates.managers.UpdateManager;
import nl.mingull.crates.menus.MainMenu;

public class CratesPlugin extends JavaPlugin {
	private static CratesPlugin instance;
	private Map<Integer, Manager> managers = new HashMap<>();

	@Override
	public void onLoad() {
		ConfigManager.settings(this);
	}

	@Override
	public void onEnable() {
		instance = this;
		this.saveDefaultConfig();
		var hologramManager = new HologramManager(this);
		var CrateSelectionManager = new CrateSelectionManager(this);

		var commandManager = new CommandManager(this, "crates", Arrays.asList("crate"))
				.setPermission(new Permission("crates.use"))
				.setOverridePermission(new Permission("crates.*"))
				.setPermissionMessage("<red>You do not have permission to execute this command!")
				.registerSubcommands(new ListCratesCommand(), new CreateCrateCommand(), new EditCrateCommand())
				.setExecutor(this);
		var configManager = new ConfigManager(this).registerConfig(DefaultConfig::new).load();

		// registerEvents(new CrateListener(this), hologramManager.getListener(),
		// 		CrateSelectionManager.getListener());
		registerManagers(new ChatManager(this), commandManager,
				configManager, new CrateManager(this),
				CrateSelectionManager, hologramManager,
				new KeyManager(this), new UpdateManager(this));
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(Messenger
					.format("<red>This command can only be executed by players!"));
			return true;
		}

		try {
			MenuManager.openMenu(MainMenu.class, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static CratesPlugin get() {
		return instance;
	}

	private void registerManagers(Manager... managers) {
		for (Manager manager : managers) {
			this.managers.put(manager.hashCode(), manager);
		}
	}

	private void registerEvents(Listener... listeners) {
		for (Listener listener : listeners)
			Bukkit.getPluginManager().registerEvents(listener, this);
	}

	@SuppressWarnings("unchecked")
	public <T extends Manager> T getManager(Class<T> clazz) {
		for (Manager manager : managers.values()) {
			if (clazz.isInstance(manager)) {
				return (T) manager;
			}
		}
		return null;
	}

	public <T extends Config> T getConfig(Class<T> clazz) {
		return getManager(ConfigManager.class).getConfig(clazz);
	}

	// private void displayIntro() {
	// UpdateManager updateManager = getManager(UpdateManager.class);
	// CacheManager cacheManager = getManager(CacheManager.class);

	// Bukkit.getConsoleSender().sendMessage("");
	// Bukkit.getConsoleSender().sendMessage("");
	// Bukkit.getConsoleSender()
	// .sendMessage(ChatColor.DARK_GRAY + "[ " + ChatColor.YELLOW + "LootCrate"
	// + ChatColor.GREEN + " v" + this.getDescription().getVersion()
	// + ChatColor.DARK_GRAY + " ]");
	// Bukkit.getConsoleSender().sendMessage("");
	// Bukkit.getConsoleSender()
	// .sendMessage(ChatColor.DARK_GRAY + "Running " + ChatColor.YELLOW
	// + this.getServer().getName() + " v" + this.getServer().getBukkitVersion()
	// + ChatColor.DARK_GRAY + ".");
	// if (updateManager.checkForUpdates())
	// Bukkit.getConsoleSender()
	// .sendMessage(ChatColor.RED + "Update Available (v"
	// + updateManager.getNewVersion() + "). Download here: "
	// + updateManager.getResourceURL() + ChatColor.DARK_GRAY + ".");
	// Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Loaded " +
	// ChatColor.YELLOW
	// + cacheManager.getCache().size() + ChatColor.DARK_GRAY + " crate(s).");
	// if (isHologramPluginDetected(HologramPlugin.DECENT_HOLOGRAMS))
	// Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected "
	// + ChatColor.YELLOW + "DecentHolograms" + ChatColor.DARK_GRAY + ".");
	// else
	// Bukkit.getConsoleSender().sendMessage(
	// ChatColor.RED + "No Hologram Plugin Found. Disabling Hologram Feature.");
	// if (metrics != null)
	// Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "Detected "
	// + ChatColor.YELLOW + "bStats Metrics" + ChatColor.DARK_GRAY + ".");
	// Bukkit.getConsoleSender().sendMessage("");
	// }
}
