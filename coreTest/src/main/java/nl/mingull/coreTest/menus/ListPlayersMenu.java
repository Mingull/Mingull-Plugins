package nl.mingull.coreTest.menus;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import net.kyori.adventure.text.Component;
import nl.mingull.core.Utils;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.PaginatedMenu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.utils.Messenger;
import nl.mingull.coreTest.CoreTestPlugin;

public class ListPlayersMenu extends PaginatedMenu {
	public ListPlayersMenu(PlayerMenuController pmc) {
		super(pmc);
		setBackIcon(45, );
		withFirstIcon();
		withLastIcon();
	}

	@Override
	public Component getMenuTitle() {
		return Messenger.format("<red>Players");
	}

	@Override
	public List<Icon> populateMenu() {
		CoreTestPlugin.getPluginLogger().info("Populating menu with players");
		List<Icon> icons = new ArrayList<>();
		for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
			for (int i = 0; i < 100; i++) {
				Icon icon = new Icon(Material.PLAYER_HEAD);
				icon.setDisplayName(
						Messenger.format("<red>" + onlinePlayer.getName() + " <gold>#" + (i + 1)));
				icon.addLore(Messenger.format("<gray>Manage this player"));
				icon.addMetadata(meta -> {
					meta.setOwningPlayer(onlinePlayer);
				}, SkullMeta.class);
				var imp = Messenger.format("<gray>Not implemented yet!" + " <gold>#" + (i + 1));
				icon.setAction(player -> {
					player.sendMessage(Utils.PluginInfo.PREFIX_ERROR.append(imp));
				});
				icons.add(icon);
			}
		}

		return icons;
	}
}
