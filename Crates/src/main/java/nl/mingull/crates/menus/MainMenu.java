package nl.mingull.crates.menus;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;

public class MainMenu extends Menu {

	public MainMenu(PlayerMenuController pmc) {
		super(pmc);
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gray>Crate Manager");
	}

	@Override
	public Rows getRows() {
		return Rows.ONE;
	}

	@Override
	public boolean cancelClicks() {
		return true;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		Map<Integer, Icon> icons = new HashMap<>();

		icons.put(3,
				Icons.createIcon(Material.CHEST, Messenger.format("<gold>List Crates"),
						Messenger.format("<gray>Click to view a list of all crates"))
						.setAction(p -> {
							try {
								MenuManager.openMenu(CratesListMenu.class, p);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}));
		icons.put(5, Icons.createIcon(Material.EMERALD, Messenger.format("<gold>Create Crate"),
				Messenger.format("<gray>Click to create a new crate")));
		return icons;
	}

}
