package nl.mingull.core.commandKit.Argument;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;

public class ArgumentGroup implements CommandArgument {
	private List<CommandArgument> args = new ArrayList<>();
	private Boolean required = false;

	public ArgumentGroup addArgument(Argument arg) {
		args.add(arg);
		return this;
	}

	public ArgumentGroup addArgument(Consumer<Argument> argumentConsumer) {
		Argument arg = new Argument();
		argumentConsumer.accept(arg);
		args.add(arg);
		Bukkit.getLogger().info("Added argument: " + arg.getName() + " to group");
		return this;
	}

	public ArgumentGroup addArgumentGroup(ArgumentGroup argGroup) {
		args.add(argGroup);
		return this;
	}

	public ArgumentGroup addArgumentGroup(Consumer<ArgumentGroup> argGroupConsumer) {
		ArgumentGroup argGroup = new ArgumentGroup();
		argGroupConsumer.accept(argGroup);
		args.add(argGroup);
		return this;
	}

	public List<CommandArgument> getArguments() {
		return args;
	}


	/**
	 * Set the argument group as required.
	 * 
	 * @return This Argument instance for chaining.
	 */
	public ArgumentGroup setRequired() {
		this.required = true;
		return this;
	}


	/**
	 * Check if the argument group is required.
	 *
	 * @return True if required, false otherwise.
	 */
	public boolean isRequired() {
		return required;
	}

	@Override
	public String toString() {
		boolean isRequired = args.stream()
				.anyMatch(arg -> arg instanceof Argument && ((Argument) arg).isRequired());

		String prefix = isRequired ? "<" : "[";
		String suffix = isRequired ? ">" : "]";

		String argumentsString = args.stream().map(arg -> {
			if (arg instanceof ArgumentGroup) {
				return arg.toString(); // Recursively handle nested groups
			} else if (arg instanceof Argument argument) {
				return argument.getName();
			}
			return ""; // Fallback (shouldn't happen)
		}).collect(Collectors.joining("|"));

		return prefix + argumentsString + suffix;
	}
}
