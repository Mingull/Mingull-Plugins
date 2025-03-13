package nl.mingull.crates.menus;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.chatKit.Chat;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.managers.CrateManager;

public class MainMenu extends Menu {
	private final CratesPlugin plugin;

	public MainMenu(PlayerMenuController pmc) {
		super(pmc);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gray>Crate Manager");
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
				Icons.createIcon(Material.CHEST, Messenger.format("<gold>List Crates"),
						Messenger.format("<gray>Click to view a list of all crates"))
						.setAction(p -> {
							try {
								MenuManager.openMenu(CratesListMenu.class, p);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}));
		icons.put(
				5, Icons
						.createIcon(Material.EMERALD, Messenger.format("<gold>Create Crate"),
								Messenger.format("<gray>Click to create a new crate"))
						.setAction(p -> {
							p.closeInventory();
							Chat.requestInput(p, Messenger.format(
									"<blue>Enter a name of the new crate (or 'cancel' to cancel):"),
									input -> {
										if (plugin.getManager(CrateManager.class)
												.crateExists(input)) {
											p.sendMessage(Messenger.format(
													"<red>A crate with that name already exists."));
											return false;
										}
										return true;
									}, input -> {
										plugin.getManager(CrateManager.class).createCrate(input);
										p.sendMessage(Messenger.format(
												"<green>Successfully <blue>created crate <gold>"
														+ input + "</gold>."));

										p.sendMessage(Messenger.format(
												"<blue>Click <click:run_command:'/crates edit "
														+ input
														+ "'><hover:show_text:'<gray>Click to manage crate'><gold>here</gold></hover></click> to manage the crate."));
									}, () -> {
										p.sendMessage(
												Messenger.format("<red>Crate creation cancelled."));
									});
						}));
		return icons;
	}

}
