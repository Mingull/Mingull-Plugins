package nl.mingull.crates.managers;

import org.bukkit.plugin.java.JavaPlugin;
import nl.mingull.core.utils.Manager;
import nl.mingull.crates.CratesPlugin;

public class UpdateManager implements Manager {
	private final CratesPlugin plugin;

	public UpdateManager(CratesPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public JavaPlugin getPlugin() {
		return plugin;
	}
}
