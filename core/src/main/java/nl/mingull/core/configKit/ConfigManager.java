package nl.mingull.core.configKit;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.managerKit.Manager;

public class ConfigManager extends Manager {
	private final Map<String, Config> configs = new HashMap<>();
	private final Map<String, Function<JavaPlugin, Config>> registeredConfigs = new HashMap<>();

	public ConfigManager(JavaPlugin plugin) {
		super(plugin);
		this.getPlugin().getDataFolder().mkdirs(); // Ensure data folder exists
		this.getPlugin().saveDefaultConfig(); // Save the default config if not exists
	}

	/**
	 * Register a Config class so it can be instantiated automatically.
	 *
	 * @param key           The config name (without ".yml")
	 * @param configFactory A factory function that takes a plugin and returns a new
	 *                      Config instance.
	 */
	public ConfigManager registerConfig(Function<JavaPlugin, Config> configFactory) {
		Config tempConfig = configFactory.apply(getPlugin());
		registeredConfigs.put(tempConfig.getKey(), configFactory);
		return this;
	}

	public ConfigManager load() {
		for (String key : registeredConfigs.keySet()) {
			loadConfig(key);
		}
		return this;
	}

	/**
	 * Load a configuration file using a subclass of Config.
	 *
	 * @param config The Config instance to be loaded.
	 */
	public ConfigManager loadConfig(String key) {
		Function<JavaPlugin, Config> factory = registeredConfigs.get(key);
		if (factory == null) {
			getPlugin().getLogger().warning("No registered config found for key: " + key);
			return this;
		}

		Config config = factory.apply(getPlugin());
		config.reload();
		configs.put(config.getKey(), config);
		getPlugin().getLogger().info("Loaded config: " + config.getKey());
		return this;
	}

	/**
	 * Get a loaded Config object by name.
	 *
	 * @param name The name of the configuration file (without .yml).
	 * @return The Config instance, or null if not loaded.
	 */
	public Config getConfig(String name) {
		return configs.get(name);
	}

	public <T extends Config> T getConfig(Class<T> clazz) {
		return clazz.cast(configs.get(clazz.getSimpleName().toLowerCase()));
	}

	/**
	 * Save a specific configuration.
	 *
	 * @param name The name of the configuration file.
	 * @return True if saved successfully, false otherwise.
	 */
	public boolean saveConfig(String name) {
		Config config = configs.get(name);

		if (config == null) {
			getPlugin().getLogger().warning("Config " + name + " is not loaded.");
			return false;
		}

		try {
			config.save();
			getPlugin().getLogger().info("Config " + name + " saved.");
			return true;
		} catch (Exception e) {
			getPlugin().getLogger().log(Level.SEVERE, "Could not save config " + name, e);
			return false;
		}
	}

	/**
	 * Reload a specific configuration by name.
	 *
	 * @param name The name of the configuration file.
	 * @return The reloaded Config instance, or null if not loaded.
	 */
	public Config reloadConfig(String name) {
		Config config = configs.get(name);

		if (config == null) {
			getPlugin().getLogger().warning("Config " + name + " is not loaded.");
			return null;
		}

		config.reload();
		getPlugin().getLogger().info("Config " + name + " reloaded.");
		return config;
	}

	/**
	 * Save all loaded configurations.
	 */
	public void saveAllConfigs() {
		configs.values().forEach(Config::save);
	}

	/**
	 * Reload all loaded configurations.
	 */
	public void reloadAllConfigs() {
		configs.values().forEach(Config::reload);
	}
}
