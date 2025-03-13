package nl.mingull.core.commandKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a subcommand for a main command. Subcommands encapsulate their own name, description,
 * usage, and permissions.
 */
public abstract class Subcommand implements TabCompleter {
	private String name;
	private String description;
	private String usage;
	private List<Permission> permissions;
	private List<String> aliases;

	/**
	 * Empty constructor for dynamic initialization.
	 */
	public Subcommand() {
		this("", "", null, new ArrayList<>(), new ArrayList<>());
	}

	/**
	 * Constructor for immediate initialization.
	 */
	public Subcommand(@NotNull String name, @NotNull String description) {
		this(name, description, null, new ArrayList<>(), new ArrayList<>());
	}

	public Subcommand(@NotNull String name, @NotNull String description, @Nullable String usage) {
		this(name, description, usage, new ArrayList<>(), new ArrayList<>());
	}

	public Subcommand(@NotNull String name, @NotNull String description, @Nullable String usage,
			@Nullable List<Permission> permissions) {
		this(name, description, usage, permissions, new ArrayList<>());
	}

	public Subcommand(@NotNull String name, @NotNull String description, @Nullable String usage,
			@Nullable List<Permission> permissions, @Nullable List<String> aliases) {
		this.name = Objects.requireNonNull(name, "Subcommand name cannot be null or empty.");
		this.description = Objects.requireNonNull(description,
				"Subcommand description cannot be null or empty.");
		this.usage = usage;
		this.permissions = (permissions != null) ? new ArrayList<>(permissions) : new ArrayList<>();
		this.aliases = (aliases != null) ? new ArrayList<>(aliases) : new ArrayList<>();
	}

	// --- Getters ---
	public @NotNull String getName() {
		return name;
	}

	public @NotNull String getDescription() {
		return description;
	}

	public @NotNull String getUsage() {
		return usage;
	}

	public @NotNull List<Permission> getPermissions() {
		return permissions;
	}

	public @NotNull List<String> getAliases() {
		return aliases;
	}

	public boolean hasPermission(@NotNull CommandSender sender) {
		return permissions.isEmpty() || permissions.stream().allMatch(sender::hasPermission);
	}

	public boolean hasAliases() {
		return !aliases.isEmpty();
	}

	// --- Setters ---
	public void setName(@NotNull String name) {
		this.name = Objects.requireNonNull(name, "Subcommand name cannot be null or empty.");
	}

	public void setDescription(@NotNull String description) {
		this.description = Objects.requireNonNull(description,
				"Subcommand description cannot be null or empty.");
	}

	public void setUsage(@Nullable String usage) {
		this.usage = usage;
	}

	public void setPermissions(@Nullable List<Permission> permissions) {
		this.permissions = (permissions != null) ? new ArrayList<>(permissions) : new ArrayList<>();
	}

	public void setAliases(@NotNull List<String> aliases) {
		this.aliases = Objects.requireNonNull(aliases, "Aliases cannot be null.");
	}

	// --- Adders ---
	public void addPermission(@NotNull Permission permission) {
		Objects.requireNonNull(permission, "Permission cannot be null.");
		permissions.add(permission);
	}

	/**
	 * Executes the subcommand.
	 *
	 * @param sender The sender executing the command.
	 * @param args The arguments provided with the command.
	 * @return True if the command executed successfully; false otherwise.
	 */
	public abstract boolean execute(@NotNull CommandSender sender, @NotNull String[] args);

	/**
	 * Use {@link #onTab(CommandSender, Command, String, String[])} instead. This method is only
	 * here to implement the interface.
	 */
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
			@NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return onTab(sender, args);
	}

	/**
	 * Provides tab completion for the subcommand.
	 * <p>
	 * This method is called when the sender presses the tab key while typing the subcommand.
	 *
	 * @param sender The sender executing the command.
	 * @param args The arguments provided with the command.
	 * @return A list of possible completions for the subcommand, or an empty list if there are
	 *         none.
	 */
	public @Nullable List<String> onTab(@NotNull CommandSender sender, @NotNull String[] args) {
		return Collections.emptyList();
	}
}
