package nl.mingull.crates.models;

import org.bukkit.Material;

public class CrateReward {
	private final Material material;
	private final int amount;
	private final double weight;

	public CrateReward(Material material, int amount, double weight) {
		this.material = material;
		this.amount = amount;
		this.weight = weight;
	}

	public Material getMaterial() {
		return material;
	}

	public int getAmount() {
		return amount;
	}

	public double getWeight() {
		return weight;
	}

}
