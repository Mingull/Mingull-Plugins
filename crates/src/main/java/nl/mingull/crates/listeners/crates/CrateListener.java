package nl.mingull.crates.listeners.crates;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.events.CrateAccessEvent;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.managers.KeyManager;
import nl.mingull.crates.models.Crate;

public class CrateListener implements Listener {

	private final CratesPlugin plugin;

	public CrateListener(CratesPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK)
			return;
		if (e.getHand() != EquipmentSlot.HAND)
			return;
		if (p.getInventory().getItemInMainHand() == null)
			return;

		Location clickedBlockLocation = e.getClickedBlock().getLocation();

		if (plugin.getManager(CrateManager.class)
				.getCrateAtLocation(clickedBlockLocation) != null) {
			if (plugin.getManager(KeyManager.class).isKeyForCrate(
					Icon.from(p.getInventory().getItemInMainHand()),
					plugin.getManager(CrateManager.class)
							.getCrateAtLocation(clickedBlockLocation))) {
				return;
			}
			p.sendMessage(Messenger.format("<red>You need a key to open this crate!"));
		}

		e.setCancelled(true);

		Crate crate =
				plugin.getManager(CrateManager.class).getCrateAtLocation(clickedBlockLocation);

		CrateAccessEvent event =
				new CrateAccessEvent(p, crate, e.getClickedBlock().getLocation(), e.getAction());
		Bukkit.getPluginManager().callEvent(event);

		if (event.isCancelled())
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (plugin.getManager(CrateManager.class).isCrateLocation(block.getLocation())) {
			Player player = event.getPlayer();
			if (!player.hasPermission("crates.admin")) {
				event.setCancelled(true);
				player.sendMessage(Messenger.format("<red>You cannot break crates!"));
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block against = event.getBlockAgainst();
		if (plugin.getManager(CrateManager.class).isCrateLocation(against.getLocation())) {
			event.setCancelled(true);
			event.getPlayer()
					.sendMessage(Messenger.format("<red>You cannot place blocks against crates!"));
		}
	}

	@EventHandler
	public void onBlockExplode(BlockExplodeEvent event) {
		event.blockList().removeIf(block -> plugin.getManager(CrateManager.class)
				.isCrateLocation(block.getLocation()));
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.blockList().removeIf(block -> plugin.getManager(CrateManager.class)
				.isCrateLocation(block.getLocation()));
	}

	@EventHandler
	public void onInventoryMove(InventoryMoveItemEvent event) {
		if (event.getSource().getType() == InventoryType.CHEST) {
			if (event.getSource().getLocation() != null && plugin.getManager(CrateManager.class)
					.isCrateLocation(event.getSource().getLocation())) {
				event.setCancelled(true);
			}
		}
	}
}
