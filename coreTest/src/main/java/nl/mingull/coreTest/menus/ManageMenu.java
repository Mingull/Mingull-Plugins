package nl.mingull.coreTest.menus;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.utils.Messenger;

public class ManageMenu extends Menu {
	public ManageMenu(PlayerMenuController pmc) {
		super(pmc);
	}

	@Override
	public Rows getRows() {
		return Rows.THREE;
	}

	@Override
	public Component getMenuTitle() {
		return Messenger.format("<blue>Manage");
	}

	@Override
	public boolean cancelClicks() {
		return true;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		Map<Integer, Icon> icons = new HashMap<>();
		icons.put(13, renderPlayerIcon());
		return icons;
	}

	public Icon renderPlayerIcon() {
		Icon icon = new Icon(Material.PLAYER_HEAD);
		icon.setDisplayName(Messenger.format("<red>Players"));
		icon.addLore(Messenger.format("<gray>Manage the players on the server"));
		icon.setAction(player -> {
			try {
				MenuManager.openMenu(ListPlayersMenu.class, player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		return icon;
	}
}
