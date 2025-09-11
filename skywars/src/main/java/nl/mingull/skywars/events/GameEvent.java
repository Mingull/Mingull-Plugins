package nl.mingull.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import nl.mingull.skywars.game.Game;

public class GameEvent extends PlayerEvent {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    @Getter
    private final Game game;

    public GameEvent(@NotNull Player player, Game game) {
        super(player);
        this.game = game;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
