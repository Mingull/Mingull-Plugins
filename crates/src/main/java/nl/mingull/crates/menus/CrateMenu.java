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
import nl.mingull.crates.managers.KeyManager;
import nl.mingull.crates.models.Crate;

public class CrateMenu extends Menu {
	private final CratesPlugin plugin;
	private final Crate crate;

	public CrateMenu(PlayerMenuController pmc) {
		super(pmc);
		setBackIcon();
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
		this.crate = pmc.getData("crate", Crate.class);
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<green>Crates<gray> > <gold>" + crate.getDisplayName());
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
				Icons.createIcon(Material.MAP, "<green>Locations", "<gray>Click to view locations")
						.setAction(player -> {
							try {
								MenuManager.openMenu(CrateLocationsMenu.class, player);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}));
		icons.put(4,
				Icons.createIcon(Material.CHEST, "<gold>Rewards", "<gray>Click to view rewards")
						.setAction(player -> {
							try {
								MenuManager.openMenu(CrateRewardMenu.class, player);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}));
		icons.put(5,
				Icons.createIcon(Material.TRIAL_KEY, "<blue>Get Key", "<gray>Click to get a key")
						.setAction(p -> {
							var key = plugin.getManager(KeyManager.class).getKey(crate);
							var leftOvers = p.getInventory().addItem(key.getItem());
							if (!leftOvers.isEmpty()) {
								leftOvers.values().forEach(
										item -> p.getWorld().dropItem(p.getLocation(), item));
							}
							p.sendMessage(Messenger.format(
									"<green>Received a key for <gold>" + crate.getDisplayName()));
						}));
		return icons;
	}
}
