package nl.mingull.core.menuKit;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.exceptions.MenuManagerException;
import nl.mingull.core.menuKit.exceptions.MenuManagerNotCreatedException;

public abstract class Menu implements InventoryHolder {
	private Inventory inventory;
	private Map<Integer, Icon> icons;
	protected PlayerMenuController playerMenuController;
	protected Player player;
	protected MenuBorder border;

	public Menu(PlayerMenuController pmc) {
		this.playerMenuController = pmc;
		this.player = pmc.getOwner();
		this.icons = new HashMap<>();
		this.border = new MenuBorder();
	}

	public void open() {
		inventory = Bukkit.createInventory(this, getRows().getSlots(), getMenuTitle());

		if (border.getBorderSlots().size() > 0) {
			border.getBorder().forEach((slot, icon) -> {
				this.icons.put(slot, icon);
				inventory.setItem(slot, icon.getItem());
			});
		}
		this.getIcons().forEach((slot, icon) -> {
			this.icons.put(slot, icon);
			inventory.setItem(slot, icon.getItem());
		});

		player.openInventory(inventory);
		playerMenuController.pushMenu(this);
	}

	public void back() throws MenuManagerException, MenuManagerNotCreatedException {
		MenuManager.openMenu((Class<? extends Menu>) playerMenuController.prevMenu().getClass()
				.asSubclass(Menu.class), playerMenuController.getOwner());
	}

	public Icon getIcon(int slot) {
		return this.icons.get(slot);
	}

	public Map<Integer, Icon> getIconsMap() {
		return this.icons;
	}

	public void click(int slot, Player player) {
		final Icon icon = this.icons.get(slot);

		if (icon != null) {
			icon.click(player);
		}
	}

	public void click(int slot, Player player, InventoryClickEvent event) {
		final Icon icon = this.icons.get(slot);

		if (icon != null) {
			icon.click(player, event);
		}
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return the title of the menu
	 */
	public abstract Component getMenuTitle();

	/**
	 * @return the rows of the menu
	 */
	public abstract Rows getRows();

	/**
	 * @return if the menu clicks should be cancelled
	 */
	public abstract boolean cancelClicks();

	/**
	 * @return the icons of the menu
	 */
	public abstract Map<Integer, Icon> getIcons();

	/**
	 * Called when the menu is closed.
	 */
	public void onClose() {}

	protected void reloadItems() {
		for (int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, null);
		}
		if (border.getBorderSlots().size() > 0) {
			border.getBorder().forEach((slot, icon) -> {
				this.icons.put(slot, icon);
				inventory.setItem(slot, icon.getItem());
			});
		}
		this.getIcons().forEach((slot, icon) -> {
			this.icons.put(slot, icon);
			inventory.setItem(slot, icon.getItem());
		});
	}

	/**
	 * reloads the menu with updated data.
	 */
	public void reload() throws MenuManagerException, MenuManagerNotCreatedException {
		player.closeInventory();
		MenuManager.openMenu(this.getClass(), player);
	}

	/**
	 * Fills the menu with the given icon.
	 * 
	 * @param icon
	 */
	public void setFiller(Icon icon) {
		for (int i = 0; i < inventory.getSize(); i++) {
			if (!icons.containsKey(i)) {
				this.icons.put(i, icon);
			}
		}
	}
}
