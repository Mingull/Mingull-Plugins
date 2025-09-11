package nl.mingull.skywars.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.mingull.skywars.configs.MessageType;
import nl.mingull.skywars.configs.Messages;
import nl.mingull.skywars.events.PlayerAttemptJoinEvent;
import nl.mingull.skywars.events.PlayerJoinGameEvent;
import nl.mingull.skywars.events.PlayerLeaveGameEvent;
import nl.mingull.skywars.game.Game;
import nl.mingull.skywars.game.GameManager;
import nl.mingull.skywars.game.GameState;

@RequiredArgsConstructor
public class GameListener implements Listener {
    private final Messages messages;
    private final GameManager manager;

    @EventHandler
    public void onAttemptJoin(PlayerAttemptJoinEvent event) {
        final Game game = event.getGame();
        final Player player = event.getPlayer();

        if (game.getPlayers().size() >= game.getSettings().getMaxPlayers()
                || game.getState() != GameState.IN_LOBBY
                || game.hasPlayer(player)) {
            event.setCancelled(true);
            player.sendMessage(messages.getMessage(MessageType.GAME_NOT_JOINABLE));
            return;
        }

        player.teleport(game.getLocations().getLobby());
    }

    @EventHandler
    public void onJoin(PlayerJoinGameEvent event) {
        final Game game = event.getGame();
        final Player player = event.getPlayer();

        game.broadcast(messages.getMessage(MessageType.GAME_JOIN, Placeholder.unparsed("player", player.getName()),
                Placeholder.unparsed("currentplayers", String.valueOf(game.getPlayers().size())),
                Placeholder.unparsed("maxplayers", String.valueOf(game.getSettings().getMaxPlayers()))));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        manager.leave(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerLeaveGameEvent event) {
        final Game game = event.getGame();
        final Player player = event.getPlayer();

        game.broadcast(messages.getMessage(MessageType.GAME_LEAVE, Placeholder.unparsed("player", player.getName()),
                Placeholder.unparsed("currentplayers", String.valueOf(game.getPlayers().size())),
                Placeholder.unparsed("maxplayers", String.valueOf(game.getSettings().getMaxPlayers()))));
    }
}