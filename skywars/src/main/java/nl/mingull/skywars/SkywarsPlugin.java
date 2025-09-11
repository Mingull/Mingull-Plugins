package nl.mingull.skywars;

import java.io.File;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.skywars.commands.SkywarsCommand;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.game.Game;
import nl.mingull.skywars.game.GameLocations;
import nl.mingull.skywars.game.GameManager;
import nl.mingull.skywars.game.GameSettings;
import nl.mingull.skywars.listeners.GameListener;

public class SkywarsPlugin extends JavaPlugin {
	private static SkywarsPlugin instance;
	private GameManager gameManager;

	@Override
	public void onLoad() {
		ConfigurationSerialization.registerClass(GameLocations.class);
		ConfigurationSerialization.registerClass(GameSettings.class);
		ConfigurationSerialization.registerClass(Game.class);

		saveResource("messages.yml", false);
	}

	@Override
	public void onEnable() {
		this.gameManager = new GameManager(this);
		gameManager.loadGames();
		final Messages messages = new Messages(new File(getDataFolder(), "messages.yml"));

		getCommand("skywars").setExecutor(new SkywarsCommand(gameManager, messages));
		getServer().getPluginManager().registerEvents(new GameListener(messages, gameManager), this);
	}

	@Override
	public void onDisable() {
		gameManager.saveGames();
	}
}
