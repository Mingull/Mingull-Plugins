package nl.mingull.crates.configs;

import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import nl.mingull.core.configKit.Config;
import nl.mingull.core.utils.Messenger;

public class MessagesConfig extends Config {

	public MessagesConfig(JavaPlugin plugin) {
		super(plugin, "messages");
	}

	public Component getMessage(String key) {
		return Messenger.format(getString(key));
	}

	@Override
	protected void applyDefaults() {
	}

}
