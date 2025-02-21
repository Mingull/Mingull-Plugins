package nl.mingull.coreTest.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import nl.mingull.core.Utils;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.core.menuKit.MenuManager;
import nl.mingull.core.utils.Messenger;
import nl.mingull.coreTest.CoreTestPlugin;
import nl.mingull.coreTest.menus.ManageMenu;

public class ManageCommand extends Subcommand {

	public ManageCommand() {
		super("manage", "Manage the server");
		addPermission(Utils.Permissions.OVERRIDE);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(Utils.PluginInfo.PREFIX_ERROR
					.append(Messenger.format("<gray>Only players can use this command!")));
			return true;
		}
		try {
			CoreTestPlugin.getPluginLogger().info("Opening ManageMenu for " + player.getName());
			MenuManager.openMenu(ManageMenu.class, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
