package nl.mingull.multibank.commands;

import java.util.stream.Collectors;
import org.bukkit.command.CommandSender;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import nl.mingull.core.commandKit.Subcommand;
import nl.mingull.multibank.MultiBankPlugin;

public class HelpCommand extends Subcommand {
	public HelpCommand() {
		super("help", "Show help for all commands");
		// addArgument(arg -> arg.setName("command").setDescription("The command to get help for")
		// .setType(String.class).isRequired());
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		// Check if a specific command help is requested
		if (args.length > 1) {
			String commandName = args[1].toLowerCase();
			Subcommand subCommand =
					MultiBankPlugin.getCommandManager().getSubcommands().get(commandName);

			if (subCommand != null && subCommand.hasPermission(sender)) {
				sendCommandHelp(sender, subCommand);
				return true;
			} else {
				sender.sendMessage(Component.text("Command not found or inaccessible: ")
						.color(NamedTextColor.RED).append(Component.text(commandName)
								.color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD)));
				return true;
			}
		}

		// Display general help
		sendGeneralHelp(sender);
		return true;
	}

	/**
	 * Sends detailed help for a specific subcommand.
	 *
	 * @param sender The sender requesting help.
	 * @param subCommand The subcommand to display help for.
	 */
	private void sendCommandHelp(CommandSender sender, Subcommand subCommand) {
		sender.sendMessage(Component.text("=== Command Help: /ref " + subCommand.getName() + " ===")
				.color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
		sender.sendMessage(Component.text("Description: ").color(NamedTextColor.YELLOW)
				.append(Component.text(subCommand.getDescription()).color(NamedTextColor.WHITE)));
		sender.sendMessage(Component.text("Usage: ").color(NamedTextColor.YELLOW)
				.append(Component.text(subCommand.getUsage()).color(NamedTextColor.WHITE)));
		sender.sendMessage(
				Component.text("============================").color(NamedTextColor.GOLD));
	}

	/**
	 * Sends general help listing all available commands.
	 *
	 * @param sender The sender requesting help.
	 */
	private void sendGeneralHelp(CommandSender sender) {
		sender.sendMessage(Component.text("=== Referrer Commands ===").color(NamedTextColor.GOLD)
				.decorate(TextDecoration.BOLD));
		var subcommands = MultiBankPlugin.getCommandManager().getSubcommands().values().stream()
				.filter(subCommand -> subCommand.hasPermission(sender))
				.sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
				.collect(Collectors.toList());

		if (subcommands.isEmpty()) {
			sender.sendMessage(Component.text("No commands available for your permissions.")
					.color(NamedTextColor.RED));
		} else {
			sender.sendMessage(Component.text("Use ").color(NamedTextColor.YELLOW)
					.append(Component.text("/ref help <command>").color(NamedTextColor.WHITE))
					.append(Component.text(" for more info.").color(NamedTextColor.YELLOW)));

			sender.sendMessage(Component.text("Available Commands:").color(NamedTextColor.GREEN));

			for (Subcommand subCommand : subcommands) {
				sender.sendMessage(Component.text(" - ").color(NamedTextColor.AQUA).append(Component
						.text(subCommand.getName()).color(NamedTextColor.WHITE)
						.decorate(TextDecoration.BOLD)
						.hoverEvent(HoverEvent.showText(
								Component.text("Click to view usage").color(NamedTextColor.GRAY)))
						.clickEvent(ClickEvent.suggestCommand("/ref help " + subCommand.getName())))
						.append(Component.text(": ")).append(Component
								.text(subCommand.getDescription()).color(NamedTextColor.GRAY)));
			}
		}

		sender.sendMessage(
				Component.text("===========================").color(NamedTextColor.GOLD));
	}
}
