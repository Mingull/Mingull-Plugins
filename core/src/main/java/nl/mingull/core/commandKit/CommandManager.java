package nl.mingull.core.commandKit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.bukkit.command.*;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.utils.Manager;
import nl.mingull.core.utils.Messenger;

/**
 * Manages a primary command and its associated subcommands. This class serves as a centralized
 * handler for executing commands, managing permissions, and providing tab completion.
 */
public class CommandManager implements Manager, CommandExecutor, TabCompleter {
	private final JavaPlugin plugin;
	private final PluginCommand command;
	private final String commandName;
	private final List<String> aliases;
	private final Map<String, Subcommand> subcommands = new ConcurrentHashMap<>();
	private Permission mainPermission;
	private Component permissionMessage;
	private Permission overridePermission;
	private CommandExecutor executor;

	/**
	 * Constructs a new CommandManager for handling a specific command.
	 *
	 * @param plugin The plugin instance that owns the command.
	 * @param commandName The primary command name as defined in plugin.yml.
	 * @throws IllegalArgumentException If the command is not registered in plugin.yml.
	 */
	public CommandManager(JavaPlugin plugin, String commandName) {
		this(plugin, commandName, List.of());
	}

	/**
	 * Constructs a new CommandManager with support for aliases.
	 *
	 * @param plugin The plugin instance that owns the command.
	 * @param commandName The primary command name as defined in plugin.yml.
	 * @param aliases A list of alternative names (aliases) for the command.
	 * @throws IllegalArgumentException If the command is not registered in plugin.yml.
	 */
	public CommandManager(JavaPlugin plugin, String commandName, List<String> aliases) {
		this.plugin = Objects.requireNonNull(plugin, "Plugin cannot be null.");
		this.commandName = Objects.requireNonNull(commandName, "Command name cannot be null.");
		this.aliases = aliases != null ? List.copyOf(aliases) : List.of();

		this.command = Objects.requireNonNull(this.plugin.getCommand(commandName),
				"Command '" + commandName + "' is not registered in plugin.yml");

		command.setExecutor(this);
		command.setTabCompleter(this);
		command.setAliases(this.aliases);
	}

	/**
	 * Gets the name of the primary command.
	 *
	 * @return The primary command name.
	 */
	public String getCommandName() {
		return commandName;
	}

	/**
	 * Registers a new subcommand under this command manager.
	 *
	 * @param subcommand The subcommand to register.
	 * @return This CommandManager instance for method chaining.
	 */
	public CommandManager registerSubcommand(Subcommand subcommand) {
		subcommands.put(subcommand.getName().toLowerCase(), subcommand);
		return this;
	}

	/**
	 * Assigns a custom command executor for handling the base command.
	 *
	 * @param executor The command executor.
	 * @return This CommandManager instance for method chaining.
	 */
	public CommandManager setExecutor(CommandExecutor executor) {
		this.executor = executor;
		return this;
	}

	/**
	 * Assigns a simplified command executor using a lambda-based approach.
	 *
	 * @param executor A lambda-based command executor.
	 * @return This CommandManager instance for method chaining.
	 */
	public CommandManager setExecutor(SimpleCommandExecutor executor) {
		this.executor = (sender, command, alias, args) -> executor.onCommand(sender, args);
		return this;
	}

	/**
	 * Retrieves all registered subcommands.
	 *
	 * @return An unmodifiable map containing registered subcommands.
	 */
	public Map<String, Subcommand> getSubcommands() {
		return Collections.unmodifiableMap(subcommands);
	}

	/**
	 * Sets the required permission for the main command.
	 *
	 * @param permission The permission node required to execute the main command.
	 */
	public void setPermission(Permission permission) {
		this.mainPermission = permission;
		this.command.setPermission(permission.getName());
	}


	/**
	 * Sets a global override permission that allows execution of all subcommands, regardless of
	 * individual subcommand permissions.
	 *
	 * @param permission The permission required to bypass subcommand-specific permissions.
	 */
	public void setOverridePermission(Permission permission) {
		this.overridePermission = permission;
	}

	/**
	 * Defines a custom permission message sent when a player lacks the required permission.
	 *
	 * @param permissionMessage The message to display when permission is denied.
	 */
	public void setPermissionMessage(Component permissionMessage) {
		this.permissionMessage = permissionMessage;
	}

	/**
	 * Defines a custom permission message using a formatted string.
	 *
	 * @param permissionMessage The formatted message to display when permission is denied.
	 */
	public void setPermissionMessage(String permissionMessage) {
		this.permissionMessage = Messenger.format(permissionMessage);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		// Check if the sender has permission for the main command
		if (mainPermission != null && !sender.hasPermission(mainPermission)) {
			sender.sendMessage(permissionMessage != null ? permissionMessage
					: Messenger.format("<red>You don't have permission to use this command."));
			return true;
		}

		// If no arguments, run the main executor if defined
		if (args.length == 0) {
			if (executor != null) {
				return executor.onCommand(sender, command, alias, args);
			}
			sender.sendMessage(Messenger.format("<red>Usage: /" + commandName + " <subcommand>"));
			return true;
		}

		// Find the subcommand
		String subCommandName = args[0].toLowerCase();
		Subcommand subCommand = subcommands.get(subCommandName);
		if (subCommand == null) {
			sender.sendMessage(Messenger.format("<red>Unknown subcommand: " + subCommandName));
			return true;
		}

		// Check permissions for subcommands
		if (overridePermission != null && sender.hasPermission(overridePermission)) {
			return subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
		}

		if (!subCommand.hasPermission(sender)) {
			sender.sendMessage(permissionMessage != null ? permissionMessage
					: Messenger.format("<red>You don't have permission to use this command."));
			return true;
		}

		// Execute the subcommand
		return subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length));
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias,
			String[] args) {
		if (args.length == 1) {
			return subcommands.keySet().stream()
					.filter(name -> name.startsWith(args[0].toLowerCase()))
					.collect(Collectors.toList());
		} else if (args.length > 1) {
			Subcommand subCommand = subcommands.get(args[0].toLowerCase());
			if (subCommand instanceof TabCompleter) {
				return subCommand.onTabComplete(sender, command, alias,
						Arrays.copyOfRange(args, 1, args.length));
			}
		}
		return List.of();
	}

	/**
	 * Functional interface for defining simple command executors.
	 */
	@FunctionalInterface
	public interface SimpleCommandExecutor {
		boolean onCommand(CommandSender sender, String[] args);
	}
}
