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
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.managers.KeyManager;
import nl.mingull.crates.menus.CratePreviewMenu;
import nl.mingull.crates.models.Crate;

public class PreviewCrateListener implements Listener {
	private final CratesPlugin plugin;

	public PreviewCrateListener(CratesPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCratePreview(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			plugin.getLogger().info("onCratePreview: Right click block");
			return; // Ignore right clicks in this handler
		}
		if (event.getAction() != Action.LEFT_CLICK_BLOCK)
			return;

		plugin.getLogger().info("onCratePreview: Left click block");

		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		Crate crate = plugin.getManager(CrateManager.class).getCrateAtLocation(block.getLocation());
		if (crate == null)
			return;

		// Prevent preview if the player is holding a valid crate key
		if (item != null) {
			Icon key = Icon.from(item);
			if (plugin.getManager(KeyManager.class).isKeyForCrate(key, crate)) {
				return; // Don't open preview if the player is holding a key
			}
		}

		plugin.getLogger().info("Opening crate preview menu.");
		try {
			PlayerMenuController pmc = MenuManager.getPlayerMenuController(player);
			pmc.setData("crate", crate);
			MenuManager.openMenu(CratePreviewMenu.class, player);
		} catch (MenuManagerNotCreatedException | MenuManagerException e) {
			e.printStackTrace();
		}
	}
}
