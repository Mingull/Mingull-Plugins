package nl.mingull.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import nl.mingull.core.utils.Manager;

public class ConfigManager implements Manager {
	private JavaPlugin plugin;
	private Map<String, YamlConfiguration> configs = new HashMap<>();

	public ConfigManager(JavaPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getDataFolder().mkdirs(); // Ensure data folder exists
		this.plugin.saveDefaultConfig(); // Save the default config if not exists
	}

	/**
	 * Load a configuration file from a specific folder, with optional folder handling.
	 *
	 * @param folder The folder where the config is located (e.g., "messages", "settings"). Can be
	 *        null or empty for root folder.
	 * @param filename The name of the configuration file (e.g., "en.yml").
	 */
	public void loadConfig(String folder, String filename) {
		// Construct the path from folder and filename
		String path = (folder == null || folder.isEmpty()) ? "" : folder + File.separator;
		File configFile = new File(this.plugin.getDataFolder(), path + filename);

		if (!configFile.exists()) {
			this.plugin.saveResource(path + filename, false);
			plugin.getLogger().info("Config " + filename + " not found in "
					+ (folder == null ? "root" : folder) + ", creating default.");
		}

		// Now load the configuration
		loadConfig(path + filename); // Correct usage of the file path
	}


	/**
	 * Load a configuration file from any folder, or directly from the root folder if no folder is
	 * specified.
	 *
	 * @param file The name of the configuration file (e.g., "config.yml").
	 */
	public void loadConfig(String file) {
		File configFile = new File(this.plugin.getDataFolder(), file);

		if (!configFile.exists()) {
			this.plugin.saveResource(file, false);
			plugin.getLogger().info("Config " + file + " not found");
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		String name = file.substring(0, file.lastIndexOf('.'));
		configs.put(name, config);
	}

	/**
	 * Get a loaded configuration by name.
	 *
	 * @param name The name of the configuration file (e.g., "config").
	 * @return The YamlConfiguration object, or null if not loaded.
	 */
	public YamlConfiguration getConfig(String name) {
		return configs.get(name);
	}

	/**
	 * Save a specific configuration by name.
	 *
	 * @param name The name of the configuration file.
	 * @return True if saved successfully, false otherwise.
	 */
	public boolean saveConfig(String name) {
		YamlConfiguration config = configs.get(name);

		if (config == null) {
			plugin.getLogger().warning("Config " + name + " is not loaded.");
			return false;
		}

		File configFile = new File(plugin.getDataFolder(), name + ".yml");

		try {
			config.save(configFile);
			plugin.getLogger().info("Config " + name + " saved.");
			return true;
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config " + name, e);
			return false;
		}
	}

	/**
	 * Reload a specific configuration by name.
	 *
	 * @param name The name of the configuration file.
	 * @return The reloaded YamlConfiguration object, or null if not loaded.
	 */
	public YamlConfiguration reloadConfig(String name) {
		File configFile = new File(plugin.getDataFolder(), name + ".yml");

		if (!configFile.exists()) {
			plugin.getLogger().warning("Config " + name + " does not exist.");
			return null;
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		configs.put(name, config);
		plugin.getLogger().info("Config " + name + " reloaded.");
		return config;
	}

	/**
	 * Save all loaded configurations.
	 */
	public void saveAllConfigs() {
		configs.forEach((name, config) -> saveConfig(name));
	}

	/**
	 * Reload all loaded configurations.
	 */
	public void reloadAllConfigs() {
		configs.keySet().forEach(this::reloadConfig);
	}

	@Override
	public JavaPlugin getPlugin() {
		return plugin;
	}
}
