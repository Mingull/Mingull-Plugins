package nl.mingull.crates.managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.enums.Permission;
import nl.mingull.crates.models.Crate;

public class PlayerManager extends Manager {

	public PlayerManager(JavaPlugin plugin) {
		super(plugin);
	}

	public static boolean hasCrateInteractPermission(Crate crate, Player player) {
		return player.hasPermission(Permission.CRATE_INTERACT.getKey() + crate.getName())
				|| player.hasPermission(Permission.CRATE_INTERACT_ADMIN.getKey());
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}

}
