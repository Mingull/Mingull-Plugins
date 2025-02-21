package nl.mingull.crates.models;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Location;
// import net.kyori.adventure.text.Component;

public class Crate {
	private final String name;
	// private final Component displayName;
	private final Set<Location> locations;

	public Crate(String name) {
		this.name = name;
		// this.displayName = Component.text(name);
		this.locations = new HashSet<>();
	}

	public String getName() {
		return this.name;
	}

	// public Component getDisplayName() {
	// return this.displayName;
	// }

	public Set<Location> getLocations() {
		return this.locations;
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	public void removeLocation(Location location) {
		this.locations.remove(location);
	}
}
