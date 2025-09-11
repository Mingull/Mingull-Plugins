package nl.mingull.skywars.events;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import nl.mingull.skywars.game.Game;

public class PlayerLeaveGameEvent extends GameEvent {

    public PlayerLeaveGameEvent(@NotNull Player player, Game game) {
        super(player, game);
    }

}
