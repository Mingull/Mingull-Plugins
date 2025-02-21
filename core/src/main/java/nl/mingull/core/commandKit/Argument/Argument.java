package nl.mingull.core.commandKit.Argument;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class Argument implements CommandArgument {
	private String name = ""; // The name of the argument
	private String description = ""; // The description of the argument
	// private int position = 0; // The position of the argument in the command
	private Class<?> type = String.class; // The type of the argument (e.g., String, Integer)
	private boolean required = false; // Whether the argument is required
	private Permission permission = null; // The permission required to use the argument

	/**
	 * Get the name of the argument.
	 *
	 * @return The argument name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the argument.
	 *
	 * @param name The argument name.
	 * @return This Argument instance for chaining.
	 */
	public Argument setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get the description of the argument.
	 *
	 * @return The argument description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description of the argument.
	 * 
	 * @param description The argument description.
	 * @return This Argument instance for chaining.
	 */
	public Argument setDescription(String description) {
		this.description = description;
		return this;
	}

	// /**
	// * Get the position of the argument in the command.
	// *
	// * @return The argument position.
	// */
	// public int getPosition() {
	// return position;
	// }

	// /**
	// * Set the position of the argument in the command.
	// * <p>
	// * If it is the first argument, the position is 1. If it is the second argument, the position
	// is
	// * 2, and so on.
	// *
	// * @param position The argument position.
	// * @return This Argument instance for chaining.
	// */
	// public Argument setPosition(int position) {
	// this.position = position;
	// return this;
	// }

	/**
	 * Get the expected type of the argument.
	 *
	 * @return The argument type.
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Set the expected type of the argument.
	 *
	 * @param type The argument type.
	 * @return This Argument instance for chaining.
	 */
	public Argument setType(Class<?> type) {
		this.type = type;
		return this;
	}

	/**
	 * Set the argument as required.
	 * 
	 * @return This Argument instance for chaining.
	 */
	public Argument setRequired() {
		this.required = true;
		return this;
	}


	/**
	 * Check if the argument is required.
	 *
	 * @return True if required, false otherwise.
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Get the permission required to use the argument.
	 *
	 * @return The required permission.
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * Set the permission required to use the argument.
	 *
	 * @param permission The required permission.
	 * @return This Argument instance for chaining.
	 */
	public Argument setPermission(Permission permission) {
		this.permission = permission;
		return this;
	}

	/**
	 * Check if the sender has the required permission for this argument.
	 *
	 * @param sender The CommandSender to check.
	 * @return True if the sender has permission or no permission is set, false otherwise.
	 */
	public boolean hasPermission(CommandSender sender) {
		// If the permission is null, assume the argument is unrestricted
		if (permission == null) {
			return true;
		}
		// Check if the sender has the required permission
		return sender.hasPermission(permission);
	}

	/**
	 * Parse a value into the expected type.
	 *
	 * @param value The input value as a string.
	 * @return The parsed value as an Object.
	 * @throws IllegalArgumentException If the value cannot be parsed into the expected type.
	 */
	public Object parse(String value) throws IllegalArgumentException {

		if (type == String.class) {
			return value;
		} else if (type == Integer.class) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid integer: " + value);
			}
		} else if (type == Double.class) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid double: " + value);
			}

		} else if (type == Boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (type == Player.class) {
			Player player = Bukkit.getPlayer(value);
			if (player == null) {
				throw new IllegalArgumentException("Player not found: " + value);
			}
			return player;
		} else if (type == Entity.class) {
			Entity entity = Bukkit.getEntity(UUID.fromString(value));
			if (entity == null) {
				throw new IllegalArgumentException("Entity not found: " + value);
			}
			return entity;
		} else if (type == UUID.class) {
			return UUID.fromString(value);
		} else if (type == Permission.class) {
			return new Permission(value);

		} else {
			throw new IllegalArgumentException("Unsupported argument type: " + type.getName());
		}
	}

	@Override
	public String toString() {
		if (required) {
			return "<" + name + ">";
		} else {
			return "[" + name + "]";
		}
	}
}
