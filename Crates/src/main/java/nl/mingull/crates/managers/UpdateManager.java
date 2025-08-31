package nl.mingull.crates.managers;

import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.crates.CratesPlugin;

public class UpdateManager extends Manager {
	public UpdateManager(JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}

}
