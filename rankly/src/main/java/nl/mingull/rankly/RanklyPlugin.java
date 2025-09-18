package nl.mingull.rankly;

import java.io.File;
import java.util.List;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.rankly.commands.RanklyCommand;
import nl.mingull.rankly.configs.Messages;
import nl.mingull.rankly.discord.Discord;
import nl.mingull.rankly.discord.payloads.WebhookPayload;
import nl.mingull.rankly.stats.PlayerStats;
import nl.mingull.rankly.stats.StatsManager;

public class RanklyPlugin extends JavaPlugin implements Listener {
	@Getter
	private static RanklyPlugin instance;
	private final File messagesConfig = new File(getDataFolder(), "messages.yml");
	private StatsManager statsManager;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		saveMessagesConfig();
	}
	
	@Override
	public void onEnable() {
		WebhookPayload payload;
		if (Bukkit.getPluginManager()
				.getPlugin("PlaceholderAPI") != null) {
			Bukkit.getPluginManager()
					.registerEvents(this, this);
			instance = this;
			
			statsManager = new StatsManager();
			
			final Messages messages = new Messages(messagesConfig);
			
			registerCommand("rankly", new RanklyCommand(messages, statsManager));
			
			payload = WebhookPayload.builder()
					.content("Plugin enabled!")
					.username("Rankly Bot")
					.embeds(List.of(WebhookPayload.Embed.builder()
							.title("PlaceholderAPI Check")
							.description("Checking for PlaceholderAPI")
							.author(WebhookPayload.Author.of(getPluginMeta().getAuthors()
									.getFirst()))
							.fields(List.of(WebhookPayload.Field.builder()
									.name("Placeholder-API")
									.value("Found")
									.build()))
							.build()))
					.build();
			
		} else {
			getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
			
			payload = WebhookPayload.builder()
					.content("Plugin enabled!")
					.username("Rankly Bot")
					.embeds(List.of(WebhookPayload.Embed.builder()
							.title("PlaceholderAPI Check")
							.description("Checking for PlaceholderAPI")
							.author(WebhookPayload.Author.of(getPluginMeta().getAuthors()
									.getFirst()))
							.fields(List.of(WebhookPayload.Field.builder()
									.name("Placeholder-API")
									.value("Not Found")
									.build()))
							.build()))
					.build();
			Bukkit.getPluginManager()
					.disablePlugin(this);
		}
		if (getConfig().getBoolean("webhook.enabled", true)) {
			Discord.sendMessage(payload);
		}
	}
	
	@Override
	public void onDisable() {
		// gameManager.saveGames();
	}
	
	private void saveMessagesConfig() {
		if (!messagesConfig.exists()) {
			saveResource("messages.yml", false);
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		// Get the player who joined
		Player player = event.getPlayer();
		
		// Create a new PlayerStats object for the player
		PlayerStats playerStats = new PlayerStats(player.getName(), 0, player.getStatistic(Statistic.PLAYER_KILLS), player.getStatistic(Statistic.DEATHS), player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20);
		
		// Add the PlayerStats to the StatsManager
		statsManager.addPlayerStats(playerStats);
	}
}
