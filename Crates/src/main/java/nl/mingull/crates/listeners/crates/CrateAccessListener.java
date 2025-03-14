package nl.mingull.crates.listeners.crates;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.events.CrateAccessEvent;
import nl.mingull.crates.models.Crate;

public class CrateAccessListener implements Listener {
	private final CratesPlugin plugin;

	public CrateAccessListener(CratesPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCrateAccess(CrateAccessEvent event) {
		Player player = event.getPlayer();
		Crate crate = event.getCrate();
		Action action = event.getAction();
	}
}
