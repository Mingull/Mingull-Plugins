package nl.mingull.core.menuKit.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.MenuManager;

public class MenuClickListener implements Listener {
	@EventHandler
	public void onMenuClick(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory == null || !(inventory.getHolder() instanceof Menu menu)) {
			return;
		}

		if (inventory.getItem(event.getSlot()) == null) {
			return;
		}

		if (menu.cancelClicks()) {
			event.setCancelled(true);
		}

		Player player = (Player) event.getWhoClicked();

		Icon icon = menu.getIcon(event.getSlot());
		if (icon == null) {
			return;
		}
		if (icon.getEventConsumer() != null) {
			menu.click(event.getSlot(), player, event);
		} else {
			menu.click(event.getSlot(), player);
		}
	}

	@EventHandler
	public void onMenuClose(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory == null || !(inventory.getHolder() instanceof Menu menu)) {
			return;
		}

		menu.onClose();
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		try {
			Player player = event.getPlayer();
			MenuManager.removePlayerMenuController(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
