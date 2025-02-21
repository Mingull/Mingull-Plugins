package nl.mingull.crates.menus;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;

public class CratesMenu extends Menu {
	private final CratesPlugin plugin;
	// private final Crate crate;
	// private final CrateManager crateManager;

	public CratesMenu(PlayerMenuController pmc) {
		super(pmc);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
		// this.crate = crate;
		// this.crateManager = crateManager;


	}

	@Override
	public Component getMenuTitle() {
		// return Messenger.format(crate.getName());
		return Messenger.format("<green>Crates<gray> > <gold>crate");
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
		icons.put(3, Icons.createIcon(Material.MAP, "<green>Locations",
				"<gray>Click to view locations").setAction(player -> {
					try {
						MenuManager.openMenu(CrateLocationsMenu.class, player);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
		icons.put(4, Icons.createIcon(Material.CHEST, "<gold>Rewards",
				"<gray>Click to view rewards")
		// .setAction(player -> {
		// new ItemsMenu(crate).open(player);
		// })
		);
		icons.put(5, Icons.createIcon(Material.TRIPWIRE_HOOK, "<red>Delete",
				"<gray>Click to delete")
		// .setAction(player -> {
		// crate.delete();
		// player.closeInventory();
		// })
		);
		return icons;
	}
}
