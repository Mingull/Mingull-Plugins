package nl.mingull.core.menuKit;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Material;
import nl.mingull.core.menuKit.utils.BorderType;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;

/**
 * Represents a paginated menu that supports navigation buttons.
 */
public abstract class PaginatedMenu extends Menu {
	private int index = 0;
	private final int pageSize;
	private int totalPages;
	private List<Icon> cachedIcons;
	private final PropertyChangeSupport support = new PropertyChangeSupport(this);

	private boolean withFirstButton = false;
	private boolean withLastButton = false;
	private Button firstButton = new Button(new Icon(Material.ARROW,
			Messenger.format(isFirst() ? "<gray>First Page" : "<green>First page"))
					.setAction(p -> first()),
			47);
	private Button prevButton = new Button(new Icon(Material.ARROW,
			Messenger.format(hasPrev() ? "<green>Previous Page" : "<gray>Previous page"))
					.setAction(p -> prev()),
			48);
	private Button nextButton = new Button(new Icon(Material.ARROW,
			Messenger.format(hasNext() ? "<green>Next Page" : "<gray>Next page"))
					.setAction(p -> next()),
			50);
	private Button lastButton = new Button(new Icon(Material.ARROW,
			Messenger.format(isLast() ? "<gray>Last Page" : "<green>Last page"))
					.setAction(p -> last()),
			51);


	/**
	 * Creates a new paginated menu with a border.
	 *
	 * @param pmc The PlayerMenuController instance.
	 */
	public PaginatedMenu(PlayerMenuController pmc) {
		super(pmc);
		border.setBorder(BorderType.BOTTOM, Icons.GlassPane.StainedBlack, getRows());
		border.setIcon(prevButton.getSlot(), prevButton.getIcon());
		border.setIcon(nextButton.getSlot(), nextButton.getIcon());
		this.pageSize = calculatePageSize();
		support.addPropertyChangeListener(evt -> {
			if ("index".equals(evt.getPropertyName())) {
				reloadItems();
			}
		});
	}

	@Override
	public Rows getRows() {
		return Rows.SIX;
	}

	@Override
	public boolean cancelClicks() {
		return true;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		List<Icon> items = getData();
		Map<Integer, Icon> icons = new HashMap<>();

		List<Integer> validSlots = border.getValidSlots(getInventory());
		int maxIndex = Math.min(validSlots.size(), items.size() - index * pageSize);

		for (int i = 0; i < maxIndex; i++) {
			int pageIndex = index * pageSize + i;
			if (pageIndex >= items.size())
				break;

			int slot = validSlots.get(i);
			icons.put(slot, items.get(pageIndex));
		}

		return icons;
	}

	/**
	 * Retrieves the data to be paginated. Caches the data for better performance.
	 *
	 * @return List of icons representing paginated content.
	 */
	public List<Icon> getData() {
		if (cachedIcons == null) {
			cachedIcons = populateMenu();
		}
		totalPages = (int) Math.ceil((double) cachedIcons.size() / pageSize);
		return cachedIcons;
	}

	/**
	 * Populates the menu with data to be paginated.
	 *
	 * @return List of icons for pagination.
	 */
	public abstract List<Icon> populateMenu();

	/**
	 * Calculates the number of available slots for items (excluding border slots).
	 */
	private int calculatePageSize() {
		return getRows().getSlots() - border.getSize();
	}

	/**
	 * Invalidates the cached icons, forcing a reload.
	 */
	private void invalidateCache() {
		cachedIcons = null;
	}

	private void setIndex(int newIndex) {
		int oldIndex = this.index;
		this.index = newIndex;
		support.firePropertyChange("index", oldIndex, newIndex);
	}

	private void setIndex(Consumer<Integer> consumer) {
		int oldIndex = this.index;
		consumer.accept(index);
		support.firePropertyChange("index", oldIndex, index);
	}

	/**
	 * Moves to the first page if enabled.
	 *
	 * @return true if the page changed, false otherwise.
	 */
	public boolean first() {
		if (withFirstButton && !isFirst()) {
			setIndex(0);
			return true;
		}
		return false;
	}

	/**
	 * Moves to the next page if available.
	 *
	 * @return true if the page changed, false otherwise.
	 */
	public boolean next() {
		if (hasNext()) {
			setIndex((index) -> {
				index++;
			});
			reloadItems();
			return true;
		}
		return false;
	}

	/**
	 * Moves to the previous page if available.
	 *
	 * @return true if the page changed, false otherwise.
	 */
	public boolean prev() {
		if (hasPrev()) {
			index--;
			reloadItems();
			return true;
		}
		return false;
	}

	/**
	 * Moves to the last page if enabled.
	 *
	 * @return true if the page changed, false otherwise.
	 */
	public boolean last() {
		if (withLastButton && !isLast()) {
			index = totalPages - 1;
			reloadItems();
			return true;
		}
		return false;
	}

	/**
	 * @return {@code true} if the current page is the first page.
	 */
	public boolean isFirst() {
		return index == 0;
	}

	/**
	 * @return {@code true} if there is a next page.
	 */
	public boolean hasNext() {
		return totalPages > 1 && index < totalPages - 1;
	}

	/**
	 * @return {@code true} if there is a previous page.
	 */
	public boolean hasPrev() {
		return index > 0;
	}

	/**
	 * @return {@code true} if the current page is the last page.
	 */
	public boolean isLast() {
		return index == totalPages - 1;
	}

	@Override
	public void open() {
		invalidateCache();
		super.open();
	}

	/**
	 * Enable the to first page button.
	 */
	public void withFirstIcon() {
		this.withFirstButton = true;
		setFirstIcon(firstButton.getSlot(), firstButton.getIcon());
	}

	/**
	 * Enable the to first page button with custom border slot and icon.
	 * 
	 * @param slot The slot to place the icon. Must be on the border. default is 47.
	 * @param icon The icon to place.
	 */
	public void setFirstIcon(int slot, Icon icon) {
		this.withFirstButton = true;
		this.firstButton = new Button(icon, slot);
		border.setIcon(firstButton.getSlot(), firstButton.getIcon());
	}

	/**
	 * Set the previous icon.
	 * 
	 * @param slot The slot to place the icon. Must be on the border. default is 48.
	 * @param icon The icon to place.
	 */
	public void setPrevIcon(int slot, Icon icon) {
		this.prevButton = new Button(icon, slot);
		border.setIcon(prevButton.getSlot(), prevButton.getIcon());
	}

	/**
	 * Set the next icon.
	 * 
	 * @param slot The slot to place the icon. Must be on the border. default is 50.
	 * @param icon The icon to place.
	 */
	public void setNextIcon(int slot, Icon icon) {
		this.nextButton = new Button(icon, slot);
		border.setIcon(nextButton.getSlot(), nextButton.getIcon());
	}

	/**
	 * Enable the to last page button.
	 */
	public void withLastIcon() {
		this.withLastButton = true;
		setLastIcon(lastButton.getSlot(), lastButton.getIcon());
	}

	/**
	 * Enable the to last page button with custom border slot and icon.
	 * 
	 * @param slot The slot to place the icon. Must be on the border. default is 51.
	 * @param icon The icon to place.
	 */
	public void setLastIcon(int slot, Icon icon) {
		this.withLastButton = true;
		this.lastButton = new Button(icon, slot);
		border.setIcon(lastButton.getSlot(), lastButton.getIcon());
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
