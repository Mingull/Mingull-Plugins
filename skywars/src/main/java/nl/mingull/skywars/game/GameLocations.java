package nl.mingull.skywars.game;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

public class GameLocations implements ConfigurationSerializable {
    private Location lobby;
    private Map<GameTeam, Location> spawns = new HashMap<>();

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setSpawns(Map<GameTeam, Location> spawns) {
        this.spawns = spawns;
    }

    public Map<GameTeam, Location> getSpawns() {
        return spawns;
    }

    public void setTeamSpawn(GameTeam team, Location spawn) {
        this.spawns.put(team, spawn);
    }

    public static GameLocations deserialize(Map<String, Object> data) {
        final GameLocations locations = new GameLocations();
        locations.setLobby((Location) data.get("lobby"));
        @SuppressWarnings("unchecked")
        final Map<String, Location> temp = (Map<String, Location>) data.get("spawns");
        for (String teamString : temp.keySet()) {
            final GameTeam team = GameTeam.valueOf(teamString);
            locations.setTeamSpawn(team, temp.get(teamString));
        }
        return locations;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        final Map<String, Location> temp = new HashMap<>();

        for (GameTeam team : getSpawns().keySet()) {
            temp.put(team.name(), getSpawns().get(team));
        }

        return Map.of("lobby", getLobby(), "spawns", temp);
    }
}
