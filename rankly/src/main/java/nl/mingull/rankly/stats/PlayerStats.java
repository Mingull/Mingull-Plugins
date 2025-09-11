package nl.mingull.rankly.stats;

import lombok.Getter;

import nl.mingull.rankly.stats.impl.IntStat;

@Getter
public class PlayerStats {
    private String name;
    private Stat joins;
    private Stat kills;
    private Stat deaths;
    private Stat playtime;

    public PlayerStats(String name, int joins, int kills, int deaths, int playtime) {
        this.name = name;
        this.joins = new IntStat(joins);
        this.kills = new IntStat(kills);
        this.deaths = new IntStat(deaths);
        this.playtime = new IntStat(playtime);
    }
}