package nl.mingull.rankly.stats;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;

public class StatsManager {
	private final Map<String, PlayerStats> playerStatsMap = new HashMap<>();

	/**
	 * Adds or replaces stats for a player.
	 * 
	 * @param stats PlayerStats to add or replace
	 */
	public void addPlayerStats(PlayerStats stats) {
		playerStatsMap.put(stats.getName(), stats);

		Bukkit.getLogger().info("Added stats for player: " + stats.getName());
	}

	/**
	 * Gets stats for a player by name.
	 * 
	 * @param name Player name
	 * @return Optional of PlayerStats
	 */
	public Optional<PlayerStats> getPlayerStats(String name) {
		return Optional.ofNullable(playerStatsMap.get(name));
	}

	/**
	 * Removes stats for a player by name.
	 * 
	 * @param name Player name
	 * @return true if removed, false if not found
	 */
	public boolean removePlayerStats(String name) {
		return playerStatsMap.remove(name) != null;
	}

	/**
	 * Returns an unmodifiable collection of all player stats.
	 * 
	 * @return Collection of PlayerStats
	 */
	public Collection<PlayerStats> getAllPlayerStats() {
		return Collections.unmodifiableCollection(playerStatsMap.values());
	}
}
