package nl.mingull.coreTest;

import java.util.Arrays;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.commandKit.CommandManager;
import nl.mingull.core.configKit.ConfigManager;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.coreTest.commands.ArgumentTestCommand;
import nl.mingull.coreTest.commands.HelpCommand;
import nl.mingull.coreTest.commands.ManageCommand;
import nl.mingull.coreTest.commands.ReloadCommand;
import nl.mingull.coreTest.commands.SettingsCommand;

public class CoreTestPlugin extends JavaPlugin implements Listener {
	private static CoreTestPlugin instance;
	private static CommandManager commandManager;
	private static ConfigManager configManager;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();

		MenuManager.create(this);

		configManager = new ConfigManager(this);
		configManager.loadConfig("config.yml");

		commandManager = new CommandManager(this, "mingullcore", Arrays.asList("mc", "mcore"));
		commandManager.setPermission(new Permission("mingullcore.use"));
		commandManager.setOverridePermission(new Permission("mingullcore.*"));
		commandManager.registerSubcommand(new ArgumentTestCommand());
		commandManager.registerSubcommand(new ReloadCommand());
		commandManager.registerSubcommand(new SettingsCommand());
		commandManager.registerSubcommand(new ManageCommand());
		commandManager.registerSubcommand(new HelpCommand());
		commandManager.setExecutor((sender,args) -> new HelpCommand().execute(sender, args));

		this.getLogger().info("Mingull Core Test has been enabled!");
	}

	@Override
	public void onDisable() {
		this.reloadConfig();
		this.saveConfig();
		this.getLogger().info("Mingull Core Test has been disabled!");
	}

	@Override
	public Logger getLogger() {
		if (!this.isEnabled()){
			return super.getLogger();
		} else if (this.getConfig().getBoolean("debugger")){
			return super.getLogger();
		} else{
			return null;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.getLogger().info("Player " + event.getPlayer().getName() + " has joined the server!");
		this.getLogger().info("Player count: " + Bukkit.getOnlinePlayers().size());
	}

	public static CoreTestPlugin getInstance() {
		return instance;
	}

	public static CommandManager getCommandManager() {
		return commandManager;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public static Logger getPluginLogger() {
		return instance.getLogger();
	}
}
