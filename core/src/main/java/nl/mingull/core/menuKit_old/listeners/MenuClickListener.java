package nl.mingull.core.menuKit_old.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import nl.mingull.core.menuKit_old.AdvancedMenu;
import nl.mingull.core.menuKit_old.AdvancedIcon;
import nl.mingull.core.menuKit_old.interfaces.Icon;
import nl.mingull.core.menuKit_old.interfaces.Menu;

public class MenuClickListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory inventory = event.getClickedInventory();
		if (inventory == null || !(inventory.getHolder() instanceof Menu menu)) {
			return;
		}

		event.setCancelled(true);
		Player player = (Player) event.getWhoClicked();

		if (menu instanceof AdvancedMenu advancedMenu) {
			Icon icon = advancedMenu.getIcon(event.getSlot());
			if (icon != null && icon instanceof AdvancedIcon advancedIcon) {
				if (advancedIcon.getEventConsumer() != null) {
					advancedMenu.click(event.getSlot(), player, event);
				} else {
					advancedMenu.click(event.getSlot(), player);
				}
				return;
			}
		}

		// For other menus or icons, handle the click normally
		menu.click(event.getSlot(), player);
	}

}
