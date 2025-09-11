package nl.mingull.skywars.commands.arguments;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.skywars.commands.ArgumentExecutor;
import nl.mingull.skywars.configs.MessageType;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.game.GameManager;

public class CreateGameArgument implements ArgumentExecutor {
    private final Messages messages;
    private final GameManager manager;

    public CreateGameArgument(Messages messages, GameManager manager) {
        this.messages = messages;
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(messages.getPrefix().append(Component.text("Usage: /skywars " + getUsage())));
            return;
        }

        final String name = args[1];

        manager.createNewGame(name, player.getLocation());

        player.sendMessage(messages.getMessage(MessageType.ENTITY_CREATED, Placeholder.unparsed("entity", "Game")));
    }

    @Override
    public String getUsage() {
        return "create <name>";
    }

    @Override
    public String getDescription() {
        return "Creates a new game.";
    }

}
