package nl.mingull.core.menuKit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.inventory.Inventory;
import nl.mingull.core.menuKit.utils.BorderType;

public class MenuBorder {
	private final Map<Integer, Icon> icons;
	private BorderType type;
	private Icon icon;
	private final static Icon DEFAULT_ICON = null;

	public MenuBorder() {
		this(BorderType.NONE, DEFAULT_ICON, Rows.ONE);
	}

	public MenuBorder(BorderType type, Icon icon, Rows rows) {
		this.icons = new HashMap<>();
		this.type = type;
		this.icon = icon;
		createBorder(rows);
	}

	public void setBorder(BorderType type, Icon icon, Rows rows) {
		setBorder(type, icon, rows, false);
	}

	public void setBorder(BorderType type, Icon icon, Rows rows, boolean cancelClicks) {
		this.type = type;
		this.icon = icon;
		this.icon.setAction((p, e) -> {
			e.setCancelled(cancelClicks);
		});
		createBorder(rows);
	}

	// Sets the border of the menu
	private boolean createBorder(Rows rows) {
		if (rows == null)
			return false;
		switch (type) {
			case FULL -> setFullBorder(rows);
			case TOP -> setBorderTop(rows);
			case BOTTOM -> setBorderBottom(rows);
			case TOP_BOTTOM -> {
				setBorderTop(rows);
				setBorderBottom(rows);
			}
			case LEFT -> setBorderLeft(rows);
			case RIGHT -> setBorderRight(rows);
			case LEFT_RIGHT -> {
				setBorderLeft(rows);
				setBorderRight(rows);
			}
			case NONE -> {
			}
			default -> throw new IllegalStateException("Unexpected BorderType: " + type);
		}

		return true;
	}

	public Map<Integer, Icon> getBorder() {
		return icons;
	}

	public Set<Integer> getBorderSlots() {
		return icons.keySet();
	}

	/**
	 * Gets the valid inventory slots where items can be placed.
	 *
	 * @return List of non-border slots.
	 */
	public List<Integer> getValidSlots(Inventory inv) {
		List<Integer> slots = new ArrayList<>();
		for (int slot = 0; slot < inv.getSize(); slot++) {
			if (!getBorderSlots().contains(slot)) {
				slots.add(slot);
			}
		}
		return slots;
	}

	/**
	 * @return The size of the border.
	 */
	public int getSize() {
		return icons.size();
	}

	/**
	 * Set custom icon on the border.
	 * 
	 * @param slot The slot to place the icon. Must be on the border.
	 * @param icon The icon to place.
	 */
	public void setIcon(int slot, Icon icon) {
		try {
			if (!getBorderSlots().isEmpty() && !getBorderSlots().contains(slot)) {
				throw new MenuBorderException("Icon must be placed on the border.");
			}
		} catch (MenuBorderException e) {
			e.printStackTrace();
		}
		icons.put(slot, icon);
	}

	private void setFullBorder(Rows rows) {
		if (rows == null)
			return;
		for (int i = 0; i < rows.getSlots(); i++) {
			if (i < 9 || i % 9 == 0 || i % 9 == 8 || i > rows.getSlots() - 9) {
				if (icon != null) {
					icons.putIfAbsent(i, icon);
				}
			}
		}
	}

	private void setBorderTop(Rows rows) {
		if (rows == null)
			return;
		for (int i = 0; i < 9; i++) {
			if (icon != null) {
				icons.putIfAbsent(i, icon);
			}
		}
	}

	private void setBorderBottom(Rows rows) {
		if (rows == null)
			return;
		int size = rows.getSlots();
		for (int i = size - 9; i < size; i++) {
			if (icon != null) {
				icons.putIfAbsent(i, icon);
			}
		}
	}

	private void setBorderLeft(Rows rows) {
		if (rows == null)
			return;
		for (int i = 0; i < rows.getSlots(); i += 9) {
			if (icon != null) {
				icons.putIfAbsent(i, icon);
			}
		}
	}

	private void setBorderRight(Rows rows) {
		if (rows == null)
			return;
		for (int i = 8; i < rows.getSlots(); i += 9) {
			if (icon != null) {
				icons.putIfAbsent(i, icon);
			}
		}
	}

	public class MenuBorderException extends Exception {
		public MenuBorderException(String message) {
			super("[MenuBorder] " + message);
		}
	}
}
