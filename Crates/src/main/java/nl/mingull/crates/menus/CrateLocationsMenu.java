package nl.mingull.crates.menus;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.PaginatedMenu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.utils.BorderType;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.managers.CrateSelectionManager;
import nl.mingull.crates.models.Crate;

public class CrateLocationsMenu extends PaginatedMenu {
	private final CratesPlugin plugin;
	private final Crate crate;

	public CrateLocationsMenu(PlayerMenuController pmc) {
		super(pmc);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
		this.crate = pmc.getData("crate", Crate.class);
		border.setBorder(BorderType.FULL, Icons.GlassPane.StainedBlack, getRows());
		setBackIcon(45);
		withFirstIcon();
		withLastIcon();
		this.border.setIcon(49, Icons.createHeadIcon(
				"http://textures.minecraft.net/texture/b056bc1244fcff99344f12aba42ac23fee6ef6e3351d27d273c1572531f",
				"<green>Add location", "<gray>Click to add a location").setAction(player -> {
					plugin.getManager(CrateSelectionManager.class).startSelection(player,
							crate.getName());
					player.closeInventory();
					player.sendMessage(
							Messenger.format("<green>Right-click a block to add a crate location"));
				}));

	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gold>" + crate.getDisplayName() + " <gray>> <green>Locations");
	}

	@Override
	public List<Icon> populateMenu() {
		List<Icon> icons = new ArrayList<>();
		if (this.crate != null) {
			for (var location : this.crate.getLocations()) {
				Icon icon = Icons.createIcon(location.getBlock().getType(),
						Messenger.format("<gold>" + location.getBlock().getType().name()),
						Component.empty(),
						Messenger.format("<gray>World: " + location.getWorld().getName()),
						Messenger.format("<gray>X: " + location.getBlockX()),
						Messenger.format("<gray>Y: " + location.getBlockY()),
						Messenger.format("<gray>Z: " + location.getBlockZ()), Component.empty(),
						Messenger.format("<yellow>Click to teleport"),
						Messenger.format("<red>Right-Click to remove"));
				icon.setAction((p, e) -> {
					if (e.isRightClick()) {
						plugin.getManager(CrateManager.class).removeCrateLocation(crate.getName(),
								location);
						p.sendMessage(Messenger.format("<red>Removed location"));
						super.open(); // Refresh the menu after removing the location
					} else {
						p.teleport(location);
						p.sendMessage(Messenger.format("<green>Teleported to location"));
					}
				});
				icons.add(icon);
			}
		}
		return icons;
	}
}
