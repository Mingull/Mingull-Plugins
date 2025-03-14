package nl.mingull.core.menuKit_old;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit_old.interfaces.Icon;
import nl.mingull.core.menuKit_old.interfaces.Menu;

public class SimpleMenu implements Menu {
	private final Map<Integer, Icon> icons;
	private final Inventory inventory;
	private Player owner;

	public SimpleMenu(Rows rows) {
		this(rows, Component.text("Menu"));
	}

	public SimpleMenu(Rows rows, Component name) {
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


	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}

	public Player getOwner() {
		return owner;
	}

	public void fill(Icon icon) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (!icons.containsKey(i)) {
				setIcon(i, icon);
			}
		}
	}
}
