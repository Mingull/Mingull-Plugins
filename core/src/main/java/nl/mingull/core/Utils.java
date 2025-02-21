package nl.mingull.core;

import org.bukkit.permissions.Permission;
import net.kyori.adventure.text.Component;
import nl.mingull.core.utils.Messenger;

public final class Utils {
	// Private constructor to prevent instantiation
	private Utils() {}

	public static final class Permissions {
		private Permissions() {}

		/**
		 * Permission to override the default behavior of the plugin.
		 */
		public static final Permission OVERRIDE = new Permission("mingullcore.admin");

		/**
		 * Permission to access the settings menu.
		 */
		public static final Permission SETTINGS = new Permission("mingullcore.settings");
	}

	public static final class PluginInfo {
		private PluginInfo() {}

		public static final String NAME = "MingullCore";
		public static final String VERSION = "1.0.0";
		public static final Component PREFIX =
				Messenger.format("<gray>[<blue><bold>MC</bold><gray>] <reset>");
		public static final Component PREFIX_ERROR =
				Messenger.format("<gray>[<red><bold>MC</bold><gray>] <reset>");
	}
}
