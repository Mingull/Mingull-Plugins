package nl.mingull.core.menuKit;

import java.util.HashMap;
import java.util.Map;

import net.kyori.adventure.text.Component;

@Deprecated
public class MenuBuilder {
	private Rows rows;
	private Component name;
	private Map<Integer, Icon> icons = new HashMap<>();

	public MenuBuilder() {}

	public MenuBuilder setRows(Rows rows) {
		this.rows = rows;
		return this;
	}

	public MenuBuilder setTitle(Component name) {
		this.name = name;
		return this;
	}

	public MenuBuilder setIcon(int slot, Icon icon) {
		this.icons.put(slot, icon);
		return this;
	}

	public Menu build() {
		Menu menu = new Menu(null) {
			@Override
			public Component getTitle() {
				return name;
			}

			@Override
			public Rows getRows() {
				return rows;
			}

			@Override
			public boolean cancelClicks() {
				return false;
			}

			@Override
			public Map<Integer, Icon> getIcons() {
				return icons;
			}
		};

		return menu;
	}

	public static Menu createMenu(Rows rows, Component title) {
		return new MenuBuilder().setTitle(title).setRows(rows).build();
	}
}

