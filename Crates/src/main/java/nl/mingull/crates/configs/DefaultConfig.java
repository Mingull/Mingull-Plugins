package nl.mingull.crates.configs;

import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.configKit.Config;

public class DefaultConfig extends Config {

	public DefaultConfig(JavaPlugin plugin) {
		super(plugin, "config");
	}

	@Override
	protected void applyDefaults() {
	}

}
