package nl.mingull.skywars.game;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import nl.mingull.skywars.events.PlayerAttemptJoinEvent;
import nl.mingull.skywars.events.PlayerJoinGameEvent;
import nl.mingull.skywars.events.PlayerLeaveGameEvent;

@Getter
public class Game implements ConfigurationSerializable {
    private @Setter UUID id = UUID.randomUUID();
    private final String name;
    private final GameSettings settings;
    private final Set<GamePlayer> players = new HashSet<>();
    private @Setter GameLocations locations = new GameLocations();
    private @Setter GameState state = GameState.IN_LOBBY;

    public Game(String name, GameSettings settings) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.settings = settings;
    }

    public void join(Player player) {
        final PlayerAttemptJoinEvent attemptJoinEvent = new PlayerAttemptJoinEvent(player, this);
        Bukkit.getPluginManager().callEvent(attemptJoinEvent);

        if (!attemptJoinEvent.isCancelled()) {
            this.players.add(new GamePlayer(player));
            Bukkit.getPluginManager().callEvent(new PlayerJoinGameEvent(player, this));
        }
    }

    public void leave(Player player) {
       final boolean removed = players.removeIf(gp -> gp.getPlayer().getUniqueId().equals(player.getUniqueId()));
        if(removed) {Bukkit.getPluginManager().callEvent(new PlayerLeaveGameEvent(player, this));
        }
    }

    public void broadcast(Component message) {
        for (GamePlayer gp : getPlayers()) {
            final Player player;
            if ((player = gp.getPlayer()).isOnline())
                player.sendMessage(message);
        }
    }

    public boolean hasPlayer(Player player) {
        return getPlayers().stream()
                .map(GamePlayer::getPlayer)
                .anyMatch(p -> p.getUniqueId().equals(player.getUniqueId()));
    }

    public static Game deserialize(Map<String, Object> data) {
        final Game game = new Game((String) data.get("name"), (GameSettings) data.get("settings"));
        game.setId(UUID.fromString((String) data.get("id")));
        game.setLocations((GameLocations) data.get("locations"));
        return game;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "id", getId().toString(),
                "name", getName(),
                "settings", getSettings(),
                "locations", getLocations());
    }
}
