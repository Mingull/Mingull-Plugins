package nl.mingull.crates.models;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;

public class CrateHologram {
	private final CratesPlugin plugin;
	private final Location location;
	private final Crate crate;
	private TextDisplay hologram;

	public CrateHologram(Location location, Crate crate) {
		this.plugin = CratesPlugin.get();
		this.location = location.clone().add(.5, 1.5, .5);
		this.crate = crate;
	}

	public void show(Player player) {
		this.hologram = location.getWorld().spawn(location, TextDisplay.class, display -> {
			display.text(Messenger.format(crate.getDisplayName()
					+ "<newline><gray>Right-click to open<newline><yellow>Click to preview"));
			display.setBillboard(Display.Billboard.CENTER);
			display.setSeeThrough(false);
			display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
			display.setDefaultBackground(false);
			display.setShadowed(true);
			display.setPersistent(false);
		});

		player.showEntity(plugin, hologram);
	}

	public void hide(Player player) {
		if (hologram != null) {
			player.hideEntity(plugin, hologram);
			hologram = null;
		}
	}
}
