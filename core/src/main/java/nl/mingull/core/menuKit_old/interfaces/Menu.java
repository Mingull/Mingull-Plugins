package nl.mingull.core.menuKit_old.interfaces;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public interface Menu extends InventoryHolder {
	void open(Player player);

	void close(Player player);

	void setIcon(int slot, Icon icon);

	Icon getIcon(int slot);

	Map<Integer, Icon> getIcons();

	void click(int slot, Player player);
}
