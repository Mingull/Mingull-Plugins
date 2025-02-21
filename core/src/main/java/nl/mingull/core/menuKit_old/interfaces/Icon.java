package nl.mingull.core.menuKit_old.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Icon {
	ItemStack getItem();

	void click(Player player);

	void click(Player player, InventoryClickEvent event);
}
