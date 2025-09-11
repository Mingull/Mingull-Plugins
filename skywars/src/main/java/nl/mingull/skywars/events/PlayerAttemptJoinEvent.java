package nl.mingull.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

import nl.mingull.skywars.game.Game;

public class PlayerAttemptJoinEvent extends GameEvent implements Cancellable {
    private boolean cancelled;

    public PlayerAttemptJoinEvent(@NotNull Player player, Game game) {
        super(player, game);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
