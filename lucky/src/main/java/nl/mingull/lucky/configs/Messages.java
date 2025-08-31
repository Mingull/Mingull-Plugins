package nl.mingull.lucky.configs;

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
		this.config.options().setHeader(List.of("This configuration file contains all messages used by the plugin."));

		final String prefix = this.config.getString("prefix");
		if (prefix != null){
			this.prefix = mm.deserialize(prefix.trim());
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
		final String messageString = this.config.getString(type.getPath());

		if (messageString == null){
			return notFound;
		}

		return prefix.appendSpace().append(mm.deserialize(messageString));
	}
}
