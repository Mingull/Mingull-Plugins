package nl.mingull.multibank.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import nl.mingull.core.commandKit.Subcommand;

public class ProfilesCommand extends Subcommand {

	public ProfilesCommand() {
		super("list", "List all referral codes");
		addPermission(new Permission("referrer.list"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {

		return true;
	}
}
