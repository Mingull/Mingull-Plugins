package nl.mingull.core.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.UUID;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;

/**
 * Utility class for creating menu icons.
 */
public final class Icons {
	private Icons() {}


	/**
	 * Creates an {@link Icon} with the specified material, name, and lore.
	 * 
	 * @param material The material of the icon.
	 * @param name The name of the icon.
	 * @param lore The lore of the icon.
	 * @return An {@link Icon} with the specified material, name, and lore.
	 */
	public static final Icon createIcon(Material material, String name, String... lore) {
		return createIcon(material, Messenger.format(name),
				Arrays.stream(lore).map(Messenger::format).toArray(Component[]::new));
	}

	/**
	 * Creates an {@link Icon} with the specified material, name, and lore.
	 * 
	 * @param material The material of the icon.
	 * @param name The name of the icon.
	 * @param lore The lore of the icon.
	 * @return An {@link Icon} with the specified material, name, and lore.
	 */
	public static final Icon createIcon(Material material, Component name, Component... lore) {
		Icon icon = new Icon(material, name);
		icon.setLore(lore);
		return icon;
	}

	public static final Icon createHeadIcon(String url, String name) {
		return createHeadIcon(url, name, null, null);
	}

	public static final Icon createHeadIcon(String url, String name, String... lore) {
		return createHeadIcon(url, Messenger.format(name),
				Arrays.stream(lore).map(Messenger::format).toArray(Component[]::new));
	}

	public static final Icon createHeadIcon(String url, Component name) {
		return createHeadIcon(url, name, null, null);
	}

	public static final Icon createHeadIcon(String url, Component name, Component... lore) {
		Icon icon = new Icon(Material.PLAYER_HEAD, name);
		icon.setLore(lore);
		icon.addMetadata(meta -> {
			UUID uuid = UUID.randomUUID();
			PlayerProfile profile = Bukkit.createProfile(uuid);
			PlayerTextures textures = profile.getTextures();
			try {
				textures.setSkin(URI.create(url).toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			profile.setTextures(textures);
			meta.setPlayerProfile(profile);
		}, SkullMeta.class);

		return icon;
	}

	/**
	 * Utility class for creating glass pane icons.
	 */
	public static final class GlassPane {
		private GlassPane() {}

		/**
		 * Returns an {@link Icon} representing a glass pane. If a color is provided, it will return
		 * a stained glass pane.
		 *
		 * @param color The color (e.g., "Blue", "Gray"). Case insensitive.
		 * @return An {@link Icon} glass pane (default to gray if invalid color).
		 */
		public static Icon of(@Nullable String color) {
			if (color != null && (!color.isEmpty() || !color.matches("normal"))) {
				Material material =
						Material.matchMaterial(color.toUpperCase() + "_STAINED_GLASS_PANE");
				if (material != null) {
					return new Icon(material, Component.empty());
				}
			}
			// Default to normal glass pane
			return new Icon(Material.GLASS_PANE, Component.empty());
		}

		// Normal glass pane
		public static final Icon NORMAL = of(null);

		// Stained glass panes
		public static final Icon StainedWhite = of("white");
		public static final Icon StainedOrange = of("orange");
		public static final Icon StainedMagenta = of("magenta");
		public static final Icon StainedLightBlue = of("light_blue");
		public static final Icon StainedYellow = of("yellow");
		public static final Icon StainedLime = of("lime");
		public static final Icon StainedPink = of("pink");
		public static final Icon StainedGray = of("gray");
		public static final Icon StainedLightGray = of("light_gray");
		public static final Icon StainedCyan = of("cyan");
		public static final Icon StainedPurple = of("purple");
		public static final Icon StainedBlue = of("blue");
		public static final Icon StainedBrown = of("brown");
		public static final Icon StainedGreen = of("green");
		public static final Icon StainedRed = of("red");
		public static final Icon StainedBlack = of("black");
	}
}
