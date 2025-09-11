package nl.mingull.skywars.game;

import org.bukkit.entity.Player;

public class GamePlayer {
    private final Player player;

    /**
     * @param player
     */
    public GamePlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }
}
