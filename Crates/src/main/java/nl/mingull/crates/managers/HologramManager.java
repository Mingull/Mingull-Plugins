package nl.mingull.crates.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateHologram;

public class HologramManager extends Manager {
	private final Map<Player, Map<Location, CrateHologram>> playerHolograms;
	private static final double HOLOGRAM_RANGE = 10.0;

	public HologramManager(CratesPlugin plugin) {
		super(plugin);
		this.playerHolograms = new HashMap<>();
	}

	private boolean isInRange(Location playerLoc, Location crateLoc) {
		return playerLoc.getWorld().equals(crateLoc.getWorld())
				&& playerLoc.distanceSquared(crateLoc) <= HOLOGRAM_RANGE * HOLOGRAM_RANGE;
	}

	private void showHologramToPlayer(Player player, Location location, Crate crate) {
		playerHolograms.putIfAbsent(player, new HashMap<>());
		Map<Location, CrateHologram> holograms = playerHolograms.get(player);

		if (!holograms.containsKey(location)) {
			CrateHologram hologram = new CrateHologram(location, crate);
			holograms.put(location, hologram);
			hologram.show(player);
		}
	}

	private void hideHologramFromPlayer(Player player, Location location) {
		Map<Location, CrateHologram> holograms = playerHolograms.get(player);
		if (holograms != null) {
			CrateHologram hologram = holograms.remove(location);
			if (hologram != null) {
				hologram.hide(player);
			}
		}
	}

	public void removeAll() {
		for (Map<Location, CrateHologram> holograms : playerHolograms.values()) {
			for (CrateHologram hologram : holograms.values()) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					hologram.hide(player);
				}
			}
			holograms.clear();
		}
		playerHolograms.clear();
	}

	public HologramListener getListener() {
		return new HologramListener();
	}

	private class HologramListener implements Listener {
		@EventHandler
		public void onPlayerMove(PlayerMoveEvent event) {
			Location from = event.getFrom();
			Location to = event.getTo();

			if (to != null && (from.getBlockX() != to.getBlockX()
					|| from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ())) {
				Player player = event.getPlayer();

				for (Crate crate : ((CratesPlugin) getPlugin()).getManager(CrateManager.class).getCrates()) {
					for (Location location : crate.getLocations()) {
						if (isInRange(to, location)) {
							showHologramToPlayer(player, location, crate);
						} else {
							hideHologramFromPlayer(player, location);
						}
					}
				}
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}
}
