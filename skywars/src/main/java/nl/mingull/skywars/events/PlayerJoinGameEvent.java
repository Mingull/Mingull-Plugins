package nl.mingull.skywars.events;

import org.bukkit.entity.Player;

import nl.mingull.skywars.game.Game;

public class PlayerJoinGameEvent extends GameEvent {
    public PlayerJoinGameEvent(Player player, Game game) {
        super(player, game);
    }

}
