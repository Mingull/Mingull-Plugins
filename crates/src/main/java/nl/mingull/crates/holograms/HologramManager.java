package nl.mingull.crates.holograms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;

public class HologramManager implements Listener {
	private final CratesPlugin plugin;
	private static final double HOLOGRAM_RANGE = 10.0;
	private final Map<UUID, CrateHologram> holograms;

	public HologramManager(CratesPlugin plugin) {
		this.plugin = plugin;
		this.holograms = new HashMap<>();
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();

		if (to != null && (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY()
				|| from.getBlockZ() != to.getBlockZ())) {
			for (Crate crate : plugin.getCrateManager().getCrates()) {
				for (Location loc : crate.getLocations()) {
					if (loc.getWorld().equals(to.getWorld()) && loc.distance(to) < 10) {
						if (isInRange(event.getPlayer().getLocation(), loc)) {
							// show hologram

						} else {
							// hide hologram if shown
						}
					}
				}
			}
		}
	}

	private boolean isInRange(Location playerLoc, Location crateLoc) {
		return playerLoc.getWorld().equals(crateLoc.getWorld())
				&& playerLoc.distanceSquared(crateLoc) <= HOLOGRAM_RANGE * HOLOGRAM_RANGE;
	}
}
