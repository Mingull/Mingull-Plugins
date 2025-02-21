package nl.mingull.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
	public static String serializeLocation(Location locations) {
		return locations.getWorld().getName() + "," + locations.getX() + "," + locations.getY()
				+ "," + locations.getZ() + ",";
	}

	public static Location deserializeLocation(String serialized) {
		String[] parts = serialized.split(",");
		if (parts.length != 4)
			return null;

		World world = Bukkit.getWorld(parts[0]);
		if (world == null)
			return null;

		try {
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int z = Integer.parseInt(parts[3]);
			return new Location(world, x, y, z);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
