package nl.mingull.rankly.configs;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class Messages {
	private final Component notFound = Component.text("404 Message not found");
	private final MiniMessage mm = MiniMessage.miniMessage();
	private final FileConfiguration config;
	private Component prefix = Component.empty();

	public Messages(File file) {
		this.config = YamlConfiguration.loadConfiguration(file);
		this.config.options().setHeader(List.of("============================================================",
				"  Rankly Plugin - Messages Configuration", "", "  This file contains all messages used by the plugin.",
				"", "  You can use MiniMessage syntax for formatting, for example:",
				"    <red>Error</red> <yellow>Warning</yellow> <green>Success</green>", "",
				"  Placeholders (if supported by the specific message) can be",
				"  included and will be replaced at runtime.", "", "  Example:",
				"    Welcome <player_name> to the server!", "",
				"  Modify messages freely, then save & reload/restart the server", "  to apply your changes.",
				"============================================================"));

		final String prefix = this.config.getString("prefix");
		if (prefix != null){
			this.prefix = mm.deserialize(prefix.trim()).appendSpace();
		}

		try{
			this.config.save(file);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public Component getMessage(MessageType type) {
		return getMessage(type, TagResolver.empty());
	}

	public Component getMessage(MessageType type,TagResolver... resolvers) {
		if (type.isList()){
			final List<String> messages = this.config.getStringList(type.getPath());

			if (messages.isEmpty()){
				return notFound;
			}

			final StringBuilder combined = new StringBuilder();
			for (String msg : messages){
				combined.append(msg).append("\n");
			}
			// Remove last newline
			combined.setLength(combined.length() - 1);

			return prefix.append(mm.deserialize(combined.toString(), TagResolver.resolver(resolvers)));
		} else{

			final String messageString = this.config.getString(type.getPath());

			if (messageString == null){
				return notFound;
			}

			return prefix.append(mm.deserialize(messageString, TagResolver.resolver(resolvers)));
		}
	}

	public Component getPrefix() {
		return prefix;
	}
}
