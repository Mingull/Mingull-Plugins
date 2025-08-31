package nl.mingull.multibank;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
// import nl.mingull.core.ConfigManager;
import nl.mingull.core.commandKit.CommandManager;
import nl.mingull.multibank.commands.BankCommand;
import nl.mingull.multibank.commands.HelpCommand;
import nl.mingull.multibank.commands.ReloadCommand;

public class MultiBankPlugin extends JavaPlugin implements Listener {
    private static MultiBankPlugin instance;
    // private static ConfigManager configManager;
    private static CommandManager commandManager;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        instance = this;
        // configManager = new ConfigManager(this);

        commandManager = new CommandManager(this, "multibank", Arrays.asList("bank"));
        commandManager.registerSubcommand(new BankCommand());
        commandManager.registerSubcommand(new ReloadCommand());
        commandManager.registerSubcommand(new HelpCommand());
        commandManager.setExecutor((sender,args) -> new HelpCommand().execute(sender, args));

        getLogger().info("MultiBank plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MultiBank plugin has been disabled!");
        reloadConfig();
        saveConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.sendMessage(Component.text("Hello, " + event.getPlayer().getName() + "!"));
    }

    public static MultiBankPlugin getInstance() {
        return instance;
    }

    public static CommandManager getCommandManager() {
        return commandManager;
    }
}
