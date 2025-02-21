package nl.mingull.coreTest.commands;

import org.bukkit.command.CommandSender;
import nl.mingull.core.commandKit.Subcommand;

public class ArgumentTestCommand extends Subcommand {
	public ArgumentTestCommand() {
		super("test", "Test command for arguments");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("No arguments provided");
			return true;
		}

		sender.sendMessage("Arguments provided: " + String.join(", ", args));

		return true;
	}
}
