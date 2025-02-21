package nl.mingull.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class Messenger {
	/**
	 * Format a MiniMessage string to a Component. italic negation in place so item names and lore
	 * are automatic normalized
	 * 
	 * @param message MiniMessage string
	 * @return Component
	 */
	public static Component format(String message) {
		return MiniMessage.miniMessage().deserialize("<!italic>" + message);
	}

	/**
	 * Serialize a Component to a MiniMessage string
	 * 
	 * @param message Component
	 * @return MiniMessage string
	 */
	public static String toString(Component message) {
		return MiniMessage.miniMessage().serialize(message);
	}
}
