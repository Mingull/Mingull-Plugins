package nl.mingull.skywars.commands.arguments;

import java.util.Optional;

import org.bukkit.entity.Player;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.skywars.commands.ArgumentExecutor;
import nl.mingull.skywars.configs.MessageType;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.game.Game;
import nl.mingull.skywars.game.GameManager;

@RequiredArgsConstructor
public class JoinGameArgument implements ArgumentExecutor {
    private final Messages messages;
    private final GameManager manager;

    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(messages.getPrefix().append(Component.text("Usage: /lucky " + getUsage())));
            return;
        }

        final Optional<Game> optionalGame = manager.getGame(args[1]);

        if (optionalGame.isEmpty()) {
            player.sendMessage(
                    messages.getMessage(MessageType.ENTITY_NOT_FOUND, Placeholder.unparsed("entity", "Game"),
                            Placeholder.unparsed("value", args[1])));
            return;
        }

        final Game game = optionalGame.get();
        manager.join(game, player);
    }

    @Override
    public String getUsage() {
        return "join <game>";
    }

    @Override
    public String getDescription() {
        return "Join a game.";
    }
}
