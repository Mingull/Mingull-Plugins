package nl.mingull.crates.managers;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.configs.MessagesConfig;

public class MessageManager extends Manager {
	public MessageManager(CratesPlugin plugin) {
		super(plugin);
	}

	public void sendMessage(CommandSender sender, String key) {
		sender.sendMessage(getPlugin().getConfig(MessagesConfig.class).getMessage(key));
	}

	public String parseMessage() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}
}
