package nl.mingull.rankly.stats;

import lombok.Getter;

@Getter
public class PlayerStats {
    private final String name;
    private final Stat joins;
    private final Stat kills;
    private final Stat deaths;
    private final Stat playtime;

    public PlayerStats(String name, int joins, int kills, int deaths, int playtime) {
        this.name = name;
        this.joins = new Stat("joins", joins);
        this.kills = new Stat("kills", kills);
        this.deaths = new Stat("deaths", deaths);
        this.playtime = new Stat("playtime", playtime);
    }
}