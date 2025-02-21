package nl.mingull.multibank.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import nl.mingull.core.commandKit.Subcommand;

public class BankCommand extends Subcommand {
	public BankCommand() {
		super("bank", "Open the bank GUI");
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(
					Component.text("Only players can use this command!").color(NamedTextColor.RED));
			return true;
		}

		return true;
	}
}
