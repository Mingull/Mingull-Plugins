package nl.mingull.core.managerKit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class Manager {
	private final JavaPlugin plugin;
	private final static Map<JavaPlugin, Settings> settings = new HashMap<>();

	public Manager(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unchecked")
	public <T extends JavaPlugin> T getPlugin() {
		return (T) plugin;
	}

	public static <T extends JavaPlugin> Settings settings(T plugin) {
		return settings.get(plugin);
	}

	public void enable() {
	}

	public void disable() {
	}
}
