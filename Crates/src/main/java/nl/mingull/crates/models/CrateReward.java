package nl.mingull.crates.models;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import nl.mingull.core.menuKit.Icon;

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


	public void giveReward(Player player) {
		Icon icon = new Icon(material, amount);
		HashMap<Integer, ItemStack> leftOvers = player.getInventory().addItem(icon.getItem());
		if (!leftOvers.isEmpty()) {
			leftOvers.values()
					.forEach(item -> player.getWorld().dropItem(player.getLocation(), item));
		}
	}
}
