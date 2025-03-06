package nl.mingull.core.menuKit;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.exceptions.MenuManagerException;
import nl.mingull.core.menuKit.exceptions.MenuManagerNotCreatedException;
import nl.mingull.core.utils.Messenger;

public abstract class Menu implements InventoryHolder {
	private Map<Integer, Icon> icons;
	protected Inventory inventory;
	protected PlayerMenuController playerMenuController;
	protected Player player;
	protected MenuBorder border;
	private Button backButton = null;

	public Menu(PlayerMenuController pmc) {
		this.playerMenuController = pmc;
		this.player = pmc.getOwner();
		this.icons = new HashMap<>();
		this.border = new MenuBorder();
	}

	public void open() {
		inventory = Bukkit.createInventory(this, getRows().getSlots(), getTitle());

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
	public abstract Component getTitle();

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

	/**
	 * Enable the back button on slot 0.
	 * <p>
	 * Use {@link #setBackIcon(int)} to set a custom slot.
	 */
	public void setBackIcon() {
		this.setBackIcon(0);
	}

	/**
	 * Enable the back button with custom border slot.
	 * <p>
	 * Use {@link #setBackIcon(int, Icon)} to set a custom icon.
	 * 
	 * @param slot The slot to place the icon, must be on the border.
	 */
	public void setBackIcon(int slot) {
		setBackIcon(slot, new Icon(Material.BARRIER, Messenger.format("<blue>Back")));
	}

	/**
	 * Enable the back button with custom border slot and icon.
	 * <p>
	 * You don't have to set an action, it will automatically go back to the previous
	 * 
	 * @param slot The slot to place the icon. Must be on the border. default is 0.
	 * @param icon The icon to place.
	 */
	public void setBackIcon(int slot, Icon icon) {
		icon.setAction(p -> {
			try {
				back();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		this.backButton = new Button(icon, slot);
		border.setIcon(backButton.getSlot(), backButton.getIcon());
	}

	/**
	 * Represents a button inside the paginated menu.
	 */
	public static class Button {
		private final int slot;
		private final Icon icon;

		/**
		 * Creates a new Button.
		 *
		 * @param icon The base icon.
		 * @param slot The slot where the button is placed.
		 */
		public Button(Icon icon, int slot) {
			this.icon = icon;
			this.slot = slot;
		}

		/**
		 * @return The slot where the button is placed.
		 */
		public int getSlot() {
			return slot;
		}

		/**
		 * @return the icon
		 */
		public Icon getIcon() {
			return icon;
		}
	}
}
