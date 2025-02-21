package nl.mingull.crates.menus;

import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.PaginatedMenu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;

public class CrateLocationsMenu extends PaginatedMenu {
	private final CratesPlugin plugin;
	// private final Crate crate;
	// private final CrateManager crateManager;

	public CrateLocationsMenu(PlayerMenuController pmc) {
		super(pmc);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);

		this.border.setIcon(49, Icons.createHeadIcon(
				"http://textures.minecraft.net/texture/b056bc1244fcff99344f12aba42ac23fee6ef6e3351d27d273c1572531f",
				"<green>Add location", "<!italic><gray>Click to add a location")
				.setAction(player -> {
					player.closeInventory();
					player.sendMessage(Messenger.format("<red>Click a block to add a location"));
				}));

		// this.crate = crate;
		// this.crateManager = crateManager;
		// this.borderBottom(Icons.GlassPane.StainedBlue);
		// this.setData(getLocationIcons());
		// this.setIcon(47, Icons.createIcon(Material.ARROW, "<!italic><green>Previous",
		// "<!italic><gray>Go to the previous page."));
		// this.setIcon(49, Icons.createHeadIcon(
		// "http://textures.minecraft.net/texture/b056bc1244fcff99344f12aba42ac23fee6ef6e3351d27d273c1572531f",
		// "<green>Add location", "<!italic><gray>Click to add a location")
		// // .setAction(player -> {
		// // player.closeInventory();
		// // player.sendMessage(Messenger.format("<red>Click a block to add a location"));

		// // })
		// );
		// this.setIcon(51, Icons.createIcon(Material.ARROW, "<!italic><green>Next",
		// "<!italic><gray>Go to the next page."));

	}

	@Override
	public List<nl.mingull.core.menuKit.Icon> populateMenu() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'populateMenu'");
	}

	@Override
	public Component getMenuTitle() {
		return Messenger.format("<green>Locations");
	}


	// private List<Icon> getLocationIcons() {
	// List<Icon> icons = new ArrayList<>();
	// if (this.crate != null) {
	// for (var location : this.crate.getLocations()) {
	// AdvancedIcon icon = Icons.createIcon(location.getBlock().getType(),
	// Messenger.format("<!italic><gold>" + location.getBlock().getType().name()),
	// Component.empty(),
	// Messenger.format("<!italic><gray>World: " + location.getWorld().getName()),
	// Messenger.format("<!italic><gray>X: " + location.getBlockX()),
	// Messenger.format("<!italic><gray>Y: " + location.getBlockY()),
	// Messenger.format("<!italic><gray>Z: " + location.getBlockZ()),
	// Component.empty(), Messenger.format("<!italic><yellow>Click to teleport"),
	// Messenger.format("<!italic><red>Right-Click to remove"));
	// icon.setAction((p, e) -> {
	// if (e.isRightClick()) {
	// crateManager.removeCrateLocation(crate.getName(), location);
	// p.sendMessage(Messenger.format("<red>Removed location"));
	// reload(); // Refresh the menu after removing the location
	// } else {
	// p.teleport(location);
	// p.sendMessage(Messenger.format("<green>Teleported to location"));
	// }
	// });
	// icons.add(icon);
	// }
	// }
	// return icons;
	// }

}
