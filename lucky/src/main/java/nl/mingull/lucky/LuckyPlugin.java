package nl.mingull.lucky;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.lucky.configs.Messages;

public class LuckyPlugin extends JavaPlugin {
	@Override
	public void onLoad() {
		saveResource("messages.yml", false);
	}

	@Override
	public void onEnable() {
		final Messages messages = new Messages(new File(getDataFolder(), "messages.yml"));
	}
}
