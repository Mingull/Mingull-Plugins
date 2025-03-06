package nl.mingull.crates.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Location;

public class Crate {
	private final String name;
	private String displayName;
	private final Set<Location> locations;
	private final List<CrateReward> rewards;

	public Crate(String name) {
		this.name = name;
		this.locations = new HashSet<>();
		this.rewards = new ArrayList<>();
	}

	public String getName() {
		return this.name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName != null ? this.displayName : this.name;
	}

	public Set<Location> getLocations() {
		return this.locations;
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}

	public void removeLocation(Location location) {
		this.locations.remove(location);
	}

	public List<CrateReward> getRewards() {
		return this.rewards;
	}

	public void addReward(CrateReward reward) {
		this.rewards.add(reward);
	}

	public void removeReward(CrateReward reward) {
		this.rewards.remove(reward);
	}

	public CrateReward getRandomReward() {
		if (rewards.isEmpty()) {
			return null;
		}
		double totalWeight = rewards.stream().mapToDouble(CrateReward::getWeight).sum();

		double random = Math.random() * totalWeight;
		double currentWeight = 0;
		for (var reward : rewards) {
			currentWeight += reward.getWeight();
			if (random <= currentWeight) {
				return reward;
			}
		}

		return rewards.getLast();
	}
}
