package nl.mingull.core.configKit;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A configuration file that can be loaded and saved.
 */
public abstract class Config {
	private final JavaPlugin plugin;
	private final String key;
	private final File file;
	private YamlConfiguration config;

	public Config(JavaPlugin plugin, String key) {
		this.plugin = plugin;
		this.key = key;
		this.file = new File(plugin.getDataFolder(), key + ".yml");
		reload(); // Load config on creation
	}

	/**
	 * Reloads the configuration from disk and applies defaults.
	 */
	public void reload() {
		if (!file.exists()) {
			plugin.saveResource(key + ".yml", false);
		}
		this.config = YamlConfiguration.loadConfiguration(file);
		applyDefaults();
	}

	/**
	 * Saves the configuration to disk.
	 */
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.getLogger().severe("Could not save config " + key + ".yml!");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the configuration object for accessing values.
	 *
	 * @return The Bukkit FileConfiguration.
	 */
	public FileConfiguration get() {
		return config;
	}

	/**
	 * Sets a value in the config and saves it.
	 *
	 * @param path  The config path (e.g., "settings.value").
	 * @param value The value to set.
	 */
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}

	/**
	 * Gets a value from the config.
	 *
	 * @param path The config path.
	 * @return The value (Object), or null if not found.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T get(String path, Class<T> clazz) {
		return (T) config.get(path);
	}

	/**
	 * Gets a string value from the config.
	 *
	 * @param path The config path.
	 * @return The string value, or null if not found.
	 */
	public String getString(String path) {
		return config.getString(path);
	}

	/**
	 * Gets an integer value from the config.
	 *
	 * @param path The config path.
	 * @return The integer value, or 0 if not found.
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	/**
	 * Checks if a path exists in the config.
	 *
	 * @param path The config path.
	 * @return True if the path exists, false otherwise.
	 */
	public boolean contains(String path) {
		return config.contains(path);
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public String getKey() {
		return key;
	}

	public File getFile() {
		return file;
	}

	/**
	 * Abstract method to be implemented in subclasses to set default values.
	 */
	protected abstract void applyDefaults();
}
