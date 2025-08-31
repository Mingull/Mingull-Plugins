package nl.mingull.crates.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.exceptions.MenuManagerException;
import nl.mingull.core.menuKit.exceptions.MenuManagerNotCreatedException;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.managers.KeyManager;
import nl.mingull.crates.menus.CratesOpeningMenu;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CrateKeyListener implements Listener {
	private final CratesPlugin plugin;

	public CrateKeyListener(CratesPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCrateKeyUse(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			plugin.getLogger().info("onCrateKeyUse: Left click block");
			return; // Ignore left clicks in this handler
		}
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		plugin.getLogger().info("onCrateKeyUse: Right click block");

		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		Crate crate = plugin.getManager(CrateManager.class).getCrateAtLocation(block.getLocation());
		if (crate == null)
			return;

		if (item == null) {
			event.setCancelled(true);
			return;
		}

		Icon key = Icon.from(item);
		if (!plugin.getManager(KeyManager.class).isKeyForCrate(key, crate)) {
			plugin.getLogger().info("Item in hand is NOT a key for this crate.");
			event.setCancelled(true);
			return;
		}

		event.setCancelled(true);

		CrateReward reward = crate.getRandomReward();
		if (reward == null) {
			player.sendMessage(Messenger.format("<red>There are no rewards in this crate!"));
			return;
		}

		if (key.getAmount() > 1) {
			key.setAmount(key.getAmount() - 1);
		} else {
			player.getInventory().setItemInMainHand(null);
		}

		try {
			PlayerMenuController pmc = MenuManager.getPlayerMenuController(player);
			pmc.setData("crate", crate);
			pmc.setData("reward", reward);
			MenuManager.openMenu(CratesOpeningMenu.class, player);
		} catch (MenuManagerNotCreatedException | MenuManagerException e) {
			e.printStackTrace();
			reward.giveReward(player);
			player.sendMessage(Messenger
					.format("<green>You have received " + reward.getMaterial().name() + "!"));
		}
	}
}
