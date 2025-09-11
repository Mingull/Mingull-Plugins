package nl.mingull.rankly;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.rankly.commands.RanklyCommand;
import nl.mingull.rankly.configs.Messages;

public class RanklyPlugin extends JavaPlugin implements Listener {
	private static RanklyPlugin instance;

	@Override
	public void onLoad() {
		saveResource("messages.yml", false);
	}

	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			Bukkit.getPluginManager().registerEvents(this, this);

			final Messages messages = new Messages(new File(getDataFolder(), "messages.yml"));

			registerCommand("rankly", new RanklyCommand(messages));
		} else {
			getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		// gameManager.saveGames();
	}

	public static RanklyPlugin getInstance() {
		return instance;
	}
}
