package nl.mingull.skywars.game;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import nl.mingull.skywars.SkywarsPlugin;

public class GameManager {
    private final SkywarsPlugin plugin;
    private Set<Game> games = new HashSet<>();

    public GameManager(SkywarsPlugin plugin) {
        this.plugin = plugin;
    }

    public Optional<Game> getGame(String name) {
        return games.stream().filter(g -> g.getName().equals(name)).findFirst();
    }

    public void join(Game game, Player player) {
        game.join(player);
    }

    public void leave(Player player) {
        for (Game game : games) {
            game.leave(player);
        }
    }

    public Game createNewGame(String name, Location lobby) {
        return createNewGame(name, lobby, GameSettings.DEFAULT_SETTINGS);
    }

    public Game createNewGame(String name, Location lobby, GameSettings settings) {
        final Game game = new Game(name, settings);
        game.getLocations().setLobby(lobby);
        this.games.add(game);
        saveGameToConfig(game);
        return game;
    }

    private void saveGameToConfig(Game game) {
        plugin.getConfig().set("games." + game.getId(), game);
        plugin.saveConfig();
    }

    public void saveGames() {
        for (Game game : games) {
            saveGameToConfig(game);
        }
    }

    public void loadGames() {
        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("games");

        if (section == null)
            return;

        for (String gameId : section.getKeys(false)) {
            final Game game = (Game) plugin.getConfig().get("games." + gameId);

            this.games.add(game);
        }

        plugin.getLogger().info("Loaded %d games from config.".formatted(this.games.size()));
    }
}
