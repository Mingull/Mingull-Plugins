package nl.mingull.core.menuKit;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;
import nl.mingull.core.menuKit.exceptions.MenuManagerException;
import nl.mingull.core.menuKit.exceptions.MenuManagerNotCreatedException;
import nl.mingull.core.menuKit.listeners.MenuClickListener;
import nl.mingull.core.utils.Manager;

public class MenuManager implements Manager {
	private static Map<Player, PlayerMenuController> controllers = new HashMap<>();
	private static Boolean isCreated;

	private static void registerListener(Plugin plugin) {
		boolean isAlreadyRegistered = false;
		for (RegisteredListener listener : InventoryClickEvent.getHandlerList()
				.getRegisteredListeners()) {
			if (listener.getListener() instanceof MenuClickListener) {
				isAlreadyRegistered = true;
				break;
			}
		}

		if (!isAlreadyRegistered) {
			plugin.getServer().getPluginManager().registerEvents(new MenuClickListener(), plugin);
		}
	}

	/**
	 * Creates the MenuManager
	 * 
	 * @param plugin The plugin that is creating the MenuManager
	 */
	public static void create(Plugin plugin) {
		plugin.getLogger().info("Creating MenuManager");

		registerListener(plugin);
		isCreated = true;

		plugin.getLogger().info("MenuManager created");
	}

	/**
	 * Open a menu for a player
	 * 
	 * @param menu The menu class to open
	 * @param player The player to open the menu for
	 * @throws MenuManagerException
	 * @throws MenuManagerNotCreatedException
	 */
	public static void openMenu(Class<? extends Menu> menu, Player player)
			throws MenuManagerException, MenuManagerNotCreatedException {
		try {
			menu.getConstructor(PlayerMenuController.class)
					.newInstance(getPlayerMenuController(player)).open();
		} catch (InstantiationException e) {
			throw new MenuManagerException("Failed to instantiate menu class", e);
		} catch (IllegalAccessException e) {
			throw new MenuManagerException("Illegal access while trying to instantiate menu class",
					e);
		} catch (InvocationTargetException e) {
			throw new MenuManagerException(
					"An error occurred while trying to invoke the menu class constructor", e);
		} catch (NoSuchMethodException e) {
			throw new MenuManagerException("The menu class constructor could not be found", e);
		}
	}

	/**
	 * Get the PlayerMenuController for a player
	 * 
	 * @param player The player to get the controller for
	 * @return The controller for the player
	 * @throws MenuManagerNotCreatedException
	 */
	public static PlayerMenuController getPlayerMenuController(Player player)
			throws MenuManagerNotCreatedException {
		if (!isCreated) {
			throw new MenuManagerNotCreatedException();
		}

		if (!(controllers.containsKey(player))) {
			PlayerMenuController pmc = new PlayerMenuController(player);
			controllers.put(player, pmc);

			return pmc;
		} else {
			return controllers.get(player);
		}
	}

	/**
	 * Remove the PlayerMenuController for a player
	 * 
	 * @param player The player to remove the controller for
	 * @throws MenuManagerNotCreatedException
	 */
	public static void removePlayerMenuController(Player player)
			throws MenuManagerNotCreatedException {
		PlayerMenuController pmc = getPlayerMenuController(player);
		if (pmc != null) {
			controllers.remove(pmc.getOwner());
		}
	}

	@Override
	public JavaPlugin getPlugin() {
		return null;
	}
}
