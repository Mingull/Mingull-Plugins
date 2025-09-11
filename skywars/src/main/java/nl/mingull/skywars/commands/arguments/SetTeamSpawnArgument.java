package nl.mingull.skywars.commands.arguments;

import java.util.Optional;

import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.skywars.commands.ArgumentExecutor;
import nl.mingull.skywars.configs.MessageType;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.game.Game;
import nl.mingull.skywars.game.GameManager;
import nl.mingull.skywars.game.GameTeam;

public class SetTeamSpawnArgument implements ArgumentExecutor {
    private final Messages messages;
    private final GameManager manager;

    public SetTeamSpawnArgument(Messages messages, GameManager manager) {
        this.messages = messages;
        this.manager = manager;
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 3) {
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
        final String teamArg = args[2].toUpperCase();
        try {
            final GameTeam team = GameTeam.valueOf(teamArg);
            game.getLocations().setTeamSpawn(team, player.getLocation());
            player.sendMessage(messages.getMessage(MessageType.ENTITY_CREATED,
                    Placeholder.unparsed("entity", "Team spawn")));
        } catch (IllegalArgumentException e) {
            player.sendMessage(
                    messages.getMessage(MessageType.ENTITY_NOT_FOUND, Placeholder.unparsed("argument", "team"),
                            Placeholder.unparsed("value", args[2])));
        }

    }

    @Override
    public String getUsage() {
        return "spawn <game> <team>";
    }

    @Override
    public String getDescription() {
        return "Sets the spawn location of a team.";
    }
}