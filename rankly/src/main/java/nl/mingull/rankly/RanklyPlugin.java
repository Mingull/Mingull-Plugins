package nl.mingull.rankly;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.rankly.commands.RanklyCommand;
import nl.mingull.rankly.configs.Messages;
import nl.mingull.rankly.stats.PlayerStats;
import nl.mingull.rankly.stats.StatsManager;

public class RanklyPlugin extends JavaPlugin implements Listener {
	private static RanklyPlugin instance;
	private StatsManager statsManager;

	@Override
	public void onLoad() {
		saveResource("messages.yml", false);
	}

	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
			Bukkit.getPluginManager().registerEvents(this, this);
			instance = this;

			statsManager = new StatsManager();

			final Messages messages = new Messages(new File(getDataFolder(), "messages.yml"));

			registerCommand("rankly", new RanklyCommand(messages, statsManager));
		} else{
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

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Get the player who joined
		Player player = event.getPlayer();

		// Create a new PlayerStats object for the player
		PlayerStats playerStats = new PlayerStats(player.getName(), 0, player.getStatistic(Statistic.PLAYER_KILLS),
				player.getStatistic(Statistic.DEATHS), player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);

		// Add the PlayerStats to the StatsManager
		statsManager.addPlayerStats(playerStats);
	}
}
