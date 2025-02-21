package nl.mingull.core.menuKit_old;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit_old.interfaces.Icon;

/**
 * Represents a paginated menu that allows navigation between multiple pages of icons.
 * <p>
 * This menu automatically calculates page sizes and maintains a cache for performance.
 * </p>
 */
public class PaginatedMenu extends AdvancedMenu {
	private int index;
	private int pageSize;
	private int totalPages;
	private final Map<Integer, Icon> cachedIcons = new HashMap<>();
	private final Map<Integer, List<Icon>> cachedPages = new HashMap<>();
	private boolean hasBorder, hasTopBorder, hasBottomBorder, hasLeftBorder, hasRightBorder;

	/**
	 * Constructs a new paginated menu with the specified name.
	 *
	 * @param name The title of the menu.
	 */
	public PaginatedMenu(Component name) {
		super(Rows.SIX, name);
		this.index = 0;
		calculatePageSize();
	}

	/**
	 * Renders the current page by placing icons in valid slots and managing pagination.
	 */
	public void render() {
		clearPage();
		totalPages = (int) Math.ceil((double) cachedIcons.size() / pageSize);
		index = Math.max(0, Math.min(index, totalPages - 1));

		List<Icon> currentPageIcons = cachedPages.computeIfAbsent(index, key -> {
			List<Icon> icons = new ArrayList<>();
			int start = index * pageSize;
			int end = Math.min(start + pageSize, cachedIcons.size());
			for (int i = start; i < end; i++) {
				icons.add(cachedIcons.get(i));
			}
			return icons;
		});

		List<Integer> validSlots = getValidSlots();
		for (int i = 0; i < currentPageIcons.size() && i < validSlots.size(); i++) {
			setIcon(validSlots.get(i), currentPageIcons.get(i));
		}
	}

	/**
	 * reloads the menu with updated data.
	 */
	public void reload() {
		this.getInventory().close();
		this.open(this.getOwner());
	}

	/**
	 * Retrieves valid slots for icon placement, excluding border slots.
	 *
	 * @return A list of valid slot indices.
	 */
	private List<Integer> getValidSlots() {
		List<Integer> slots = new ArrayList<>();
		int rows = getRows().rows, columns = 9;

		for (int slot = 0; slot < getInventory().getSize(); slot++) {
			int row = slot / columns, col = slot % columns;
			if ((hasBorder && (row == 0 || row == rows - 1 || col == 0 || col == columns - 1))
					|| (hasTopBorder && row == 0) || (hasBottomBorder && row == rows - 1)
					|| (hasLeftBorder && col == 0) || (hasRightBorder && col == columns - 1)) {
				continue;
			}
			slots.add(slot);
		}
		return slots;
	}

	/**
	 * Calculates the page size based on the current border configuration.
	 */
	private void calculatePageSize() {
		int totalSlots = getInventory().getSize();
		int rows = getRows().rows, columns = 9;
		int usedSlots = (hasBorder ? ((columns * 2) + ((rows - 2) * 2))
				: ((hasTopBorder ? columns : 0) + (hasBottomBorder ? columns : 0)
						+ (hasLeftBorder ? rows : 0) + (hasRightBorder ? rows : 0)));
		this.pageSize = totalSlots - usedSlots;
		this.totalPages = (int) Math.ceil((double) cachedIcons.size() / pageSize);
	}

	/** Moves to the next page if available. */
	public void next() {
		if (hasNext()) {
			index++;
			render();
		}
	}

	/** Moves to the previous page if available. */
	public void prev() {
		if (hasPrev()) {
			index--;
			render();
		}
	}

	/**
	 * @return {@code true} if there is a next page.
	 */
	private boolean hasNext() {
		return totalPages > 1 && index < totalPages - 1;
	}

	/**
	 * @return {@code true} if there is a previous page.
	 */
	private boolean hasPrev() {
		return index > 0;
	}

	/**
	 * Clears only the inner slots of the menu while preserving borders.
	 */
	public void clearPage() {
		getValidSlots().forEach(slot -> getInventory().setItem(slot, null));
	}

	/**
	 * Adds an icon to the menu.
	 *
	 * @param icon The icon to add.
	 */
	public void addIcon(Icon icon) {
		cachedIcons.put(cachedIcons.size(), icon);
	}

	/**
	 * Sets the menu's data with a given list of icons, replacing existing ones.
	 *
	 * @param icons The new list of icons.
	 */
	public void setData(List<Icon> icons) {
		cachedIcons.clear();
		for (int i = 0; i < icons.size(); i++) {
			cachedIcons.put(i, icons.get(i));
		}
	}

	@Override
	public void open(Player player) {
		super.open(player);
		render();
	}

	@Override
	public void border(Icon icon) {
		super.border(icon);
		hasBorder = true;
		calculatePageSize();
	}

	@Override
	public void borderTop(Icon icon) {
		super.borderTop(icon);
		hasTopBorder = true;
		calculatePageSize();
	}

	@Override
	public void borderBottom(Icon icon) {
		super.borderBottom(icon);
		hasBottomBorder = true;
		calculatePageSize();
	}

	@Override
	public void borderLeft(Icon icon) {
		super.borderLeft(icon);
		hasLeftBorder = true;
		calculatePageSize();
	}

	@Override
	public void borderRight(Icon icon) {
		super.borderRight(icon);
		hasRightBorder = true;
		calculatePageSize();
	}

	// /**
	// * Creates a default "Next" icon for pagination.
	// *
	// * @return The next page navigation icon.
	// */
	// public static AdvancedIcon createNextIcon() {
	// return createNavigationIcon("Next",
	// p -> ((PaginatedMenu) p.getOpenInventory().getTopInventory().getHolder()).next());
	// }

	// /**
	// * Creates a default "Previous" icon for pagination.
	// *
	// * @return The previous page navigation icon.
	// */
	// public static AdvancedIcon createPrevIcon() {
	// return createNavigationIcon("Previous",
	// p -> ((PaginatedMenu) p.getOpenInventory().getTopInventory().getHolder()).prev());
	// }

	// private static AdvancedIcon createNavigationIcon(String name,
	// java.util.function.Consumer<Player> action) {
	// AdvancedIcon icon = new AdvancedIcon(Material.ARROW);
	// icon.setDisplayName(Messenger.format("<!italic><green>" + name));
	// icon.addLore(Messenger.format("<!italic><gray>Go to the " + name.toLowerCase() + " page."));
	// icon.setAction(action);
	// return icon;
	// }
}
