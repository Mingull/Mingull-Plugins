package nl.mingull.rankly.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerStats {
    private String name;
    private Stat joins;
    private Stat kills;
    private Stat deaths;
    private Stat playtime;
}