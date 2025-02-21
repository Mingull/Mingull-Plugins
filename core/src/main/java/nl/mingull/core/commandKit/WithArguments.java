package nl.mingull.core.commandKit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import nl.mingull.core.commandKit.Argument.Argument;

/**
 * Represents a command that has arguments.
 * 
 * @Deprecated Use {@link Subcommand} instead. This class will be removed in
 * @version 1.0.0
 * @since 1.0.0
 */
public class WithArguments extends Subcommand {
	private List<Argument> args = new ArrayList<>();

	public WithArguments(String name, String description) {
		super(name, description);
	}

	/**
	 * Set the arguments for this command.
	 * <p>
	 * It overrides any existing arguments.
	 * 
	 * @param args The arguments.
	 * @return This WithArgs instance for chaining.
	 */
	public WithArguments setArgs(List<Argument> args) {
		this.args = args;
		return this;
	}

	/**
	 * Add an argument to this command.
	 *
	 * @param arg The argument to add.
	 * @return This WithArgs instance for chaining.
	 */
	public WithArguments addArg(Argument arg) {
		args.add(arg);
		return this;
	}

	/**
	 * Get the arguments for this command.
	 *
	 * @return The arguments.
	 */
	public List<Argument> getArgs() {
		return args;
	}

	public String getArgsAsString() {
		return args.stream().map(
				arg -> arg.isRequired() ? "<" + arg.getName() + ">" : "[" + arg.getName() + "]")
				.collect(Collectors.joining(" "));
	}

	@Override
	public String getUsage() {
		return super.getUsage() + " " + getArgsAsString();
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias,
			String[] args) {
		if (args.length > 0) {
			Argument currentArg = this.args.get(Math.min(args.length - 1, this.args.size() - 1));
			if (currentArg.getType() == Player.class) {
				return Bukkit.getOnlinePlayers().stream().map(Player::getName)
						.filter(name -> name.startsWith(args[args.length - 1]))
						.collect(Collectors.toList());
			} else if (currentArg.getType() == Integer.class) {
				// Provide some default integer suggestions
				return List.of("1", "5", "10", "20");
			} else {
				return this.args.stream().map(Argument::getName)
						.filter(name -> name.startsWith(args[args.length - 1]))
						.collect(Collectors.toList());
			}
		}
		return List.of();
	}


	/**
	 * Executes the subcommand.
	 *
	 * @param sender The sender executing the command.
	 * @param args The arguments provided with the command.
	 * @return True if the command executed successfully; false otherwise.
	 */
	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length < this.args.stream().filter(Argument::isRequired).count()) {
			sender.sendMessage("Usage: " + getUsage());
			return false;
		}
		return true; // Proceed with execution logic in the subclass
	}

}
