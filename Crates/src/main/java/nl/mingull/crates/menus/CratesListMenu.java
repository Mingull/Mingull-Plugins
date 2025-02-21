package nl.mingull.crates.menus;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PaginatedMenu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;

public class CratesListMenu extends PaginatedMenu {

	private final CratesPlugin plugin;

	public CratesListMenu(PlayerMenuController pmc) {
		super(pmc);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
	}


	@Override
	public Component getMenuTitle() {
		return Messenger.format("<#00ff00>Crates");
	}

	@Override
	public List<Icon> populateMenu() {
		List<Icon> icons = new ArrayList<>();
		for (Crate crate : plugin.getCrateManager().getCrates()) {
			icons.add(Icons
					.createIcon(Material.CHEST,
							Messenger.format("<gold>" + crate.getName()),
							Messenger.format("<gray>Click to view crate details"))
					.addMetadata(meta -> {
						meta.getPersistentDataContainer().set(
								plugin.getCrateManager().getCrateKey(), PersistentDataType.STRING,
								crate.getName());
					}).setAction(player -> {
						try {
							MenuManager.openMenu(CratesMenu.class, player);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}));
		}
		return icons;
	}
}
