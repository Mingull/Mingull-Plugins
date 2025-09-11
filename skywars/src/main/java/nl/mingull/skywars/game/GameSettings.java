package nl.mingull.skywars.game;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class GameSettings implements ConfigurationSerializable {
    public static final GameSettings DEFAULT_SETTINGS = new GameSettings(2, 16);
    private final int minPlayers;
    private final int maxPlayers;

    public GameSettings(int minPlayers, int maxPlayers) {
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public static GameSettings deserialize(Map<String, Object> data) {
        final int minPlayers = (int) data.get("min-players");
        final int maxPlayers = (int) data.get("max-players");
        return new GameSettings(minPlayers, maxPlayers);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of("min-players", minPlayers, "max-players", maxPlayers);
    }
}
