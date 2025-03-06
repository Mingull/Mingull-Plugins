package nl.mingull.crates.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.managers.CrateManager;

public class CrateLocationSelector implements Listener {
	private final CrateManager crateManager;
	private final Map<UUID, String> playerSelections;

	public CrateLocationSelector(CrateManager manager) {
		this.crateManager = manager;
		this.playerSelections = new HashMap<>();
	}

	public void startSelection(Player player, String crateName) {
		playerSelections.put(player.getUniqueId(), crateName);
	}

	@EventHandler
	public void onBlockInteraction(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (!playerSelections.containsKey(event.getPlayer().getUniqueId()))
			return;

		event.setCancelled(true);
		Player player = event.getPlayer();
		String crateName = playerSelections.remove(event.getPlayer().getUniqueId());
		if (event.getClickedBlock() != null) {
			crateManager.addCrateLocation(crateName, event.getClickedBlock().getLocation());
			player.sendMessage(
					Messenger.format("<green>Added new location for crate: " + crateName));
		}
	}
}
