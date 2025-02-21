package nl.mingull.core.menuKit_old;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit_old.interfaces.Icon;
import nl.mingull.core.menuKit_old.interfaces.Menu;

public class AdvancedMenu implements Menu {
	private final Rows rows;
	private final Map<Integer, Icon> icons;
	private final Inventory inventory;
	private Player owner;

	public AdvancedMenu(Rows rows) {
		this(rows, Component.text("Menu"));
	}

	public AdvancedMenu(Rows rows, Component name) {
		this.rows = rows;
		this.icons = new HashMap<>();
		this.inventory = Bukkit.createInventory(this, rows.getSlots(), name);
	}

	@Override
	public void open(Player player) {
		player.openInventory(getInventory());
		this.owner = player;
	}

	@Override
	public void close(Player player) {
		player.closeInventory();
		this.owner = null;
	}

	@Override
	public void setIcon(int slot, Icon icon) {
		this.icons.put(slot, icon);
		this.inventory.setItem(slot, icon.getItem());
	}

	@Override
	public Icon getIcon(int slot) {
		return this.icons.get(slot);
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		return this.icons;
	}

	@Override
	public void click(int slot, Player player) {
		final Icon icon = this.icons.get(slot);

		if (icon != null) {
			icon.click(player);
		}
	}

	public void click(int slot, Player player, InventoryClickEvent event) {
		final AdvancedIcon icon = (AdvancedIcon) this.icons.get(slot);

		if (icon != null) {
			icon.click(player, event);
		}
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}


	public Rows getRows() {
		return rows;
	}

	public Player getOwner() {
		return owner;
	}

	/**
	 * Fills the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void fill(Icon icon) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}

	/**
	 * Fills the border of the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void border(Icon icon) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (i < 9 || i % 9 == 0 || i % 9 == 8 || i > inventory.getSize() - 9) {
				if (!icons.containsKey(i)) {
					setIcon(i, icon);
				}
			}
		}
	}

	/**
	 * Fills the top border of the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void borderTop(Icon icon) {
		for (int i = 0; i < 9; i++) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}

	/**
	 * Fills the bottom border of the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void borderBottom(Icon icon) {
		for (int i = inventory.getSize() - 9; i < inventory.getSize(); i++) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}

	/**
	 * Fills the left border of the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void borderLeft(Icon icon) {
		for (int i = 0; i < inventory.getSize(); i += 9) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}

	/**
	 * Fills the right border of the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void borderRight(Icon icon) {
		for (int i = 8; i < inventory.getSize(); i += 9) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}
}
