package nl.mingull.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Messenger {
	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	/**
	 * Formats a string into a Component, supporting both MiniMessage and legacy
	 * formatting.
	 *
	 * @param message the input string (MiniMessage or legacy formatted)
	 * @return the formatted Component
	 */
	public static Component format(String message) {
		return MINI_MESSAGE.deserialize(convertLegacy("<!italic>" + message));
	}

	/**
	 * Serializes a Component into a MiniMessage string.
	 *
	 * @param message the Component to serialize
	 * @return the MiniMessage string representation
	 */
	public static String toString(Component message) {
		return MINI_MESSAGE.serialize(message);
	}

	/**
	 * Converts a legacy-formatted string into a MiniMessage string.
	 *
	 * @param message the legacy string
	 * @return the MiniMessage string representation
	 */
	public static String convertLegacy(String message) {
		message = message.replaceAll("\u00A7", "&");
		message = message.replaceAll("&0", "<black>");
		message = message.replaceAll("&1", "<dark_blue>");
		message = message.replaceAll("&2", "<dark_green>");
		message = message.replaceAll("&3", "<dark_aqua>");
		message = message.replaceAll("&4", "<dark_red>");
		message = message.replaceAll("&5", "<dark_purple>");
		message = message.replaceAll("&6", "<gold>");
		message = message.replaceAll("&7", "<gray>");
		message = message.replaceAll("&8", "<dark_gray>");
		message = message.replaceAll("&9", "<blue>");
		message = message.replaceAll("&a", "<green>");
		message = message.replaceAll("&b", "<aqua>");
		message = message.replaceAll("&c", "<red>");
		message = message.replaceAll("&d", "<light_purple>");
		message = message.replaceAll("&e", "<yellow>");
		message = message.replaceAll("&f", "<white>");
		message = message.replaceAll("&k", "<obfuscated>");
		message = message.replaceAll("&l", "<bold>");
		message = message.replaceAll("&m", "<strikethrough>");
		message = message.replaceAll("&n", "<underline>");
		message = message.replaceAll("&o", "<italic>");
		message = message.replaceAll("&r", "<reset>");
		return message;
	}
}
