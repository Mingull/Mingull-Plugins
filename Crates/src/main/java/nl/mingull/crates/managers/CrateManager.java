package nl.mingull.crates.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CrateManager extends Manager {
	private final File cratesFile;
	private FileConfiguration cratesConfig;
	private final HashMap<String, Crate> crates;

	public CrateManager(CratesPlugin plugin) {
		super(plugin);
		this.crates = new HashMap<>();
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdirs();
		}
		this.cratesFile = new File(plugin.getDataFolder(), "crates.yml");
		this.loadCrates();
	}

	public void createCrate(String name) {
		crates.put(name, new Crate(name));
		saveCrates();
	}

	public boolean crateExists(String name) {
		return crates.containsKey(name);
	}

	public Crate getCrateByName(String name) {
		return crates.get(name);
	}

	public List<Crate> getCrates() {
		return new ArrayList<>(crates.values());
	}

	public void addCrateLocation(String name, Location location) {
		Crate crate = getCrateByName(name);
		if (crate != null) {
			crate.addLocation(location);
			saveCrates();
		}
	}

	public void removeCrateLocation(String name, Location location) {
		Crate crate = getCrateByName(name);
		if (crate != null) {
			crate.removeLocation(location);
			saveCrates();
		}
	}

	public boolean isCrateLocation(Location location) {
		return crates.values().stream()
				.anyMatch(crate -> crate.getLocations().stream().anyMatch(loc -> loc.equals(location)));
	}

	public Crate getCrateAtLocation(Location location) {
		for (Crate crate : crates.values()) {
			if (crate.getLocations().stream().anyMatch(loc -> loc.equals(location))) {
				return crate;
			}
		}

		return null;
	}

	private void loadCrates() {
		if (!cratesFile.exists()) {
			try {
				cratesFile.createNewFile();
			} catch (IOException ex) {
				this.getPlugin().getLogger().severe("Could not create crates.yml file!");
				ex.printStackTrace();
			}
		}

		this.cratesConfig = YamlConfiguration.loadConfiguration(cratesFile);
		this.crates.clear();

		var cratesSection = cratesConfig.getConfigurationSection("crates");
		if (cratesSection == null)
			return;

		for (String name : cratesSection.getKeys(false)) {
			Crate crate = new Crate(name);
			var displayName = cratesSection.getString(name + ".displayName");

			if (displayName != null) {
				crate.setDisplayName(displayName);
			}

			var locationsSection = cratesSection.getConfigurationSection(name + ".locations");
			if (locationsSection != null) {
				for (String key : locationsSection.getKeys(false)) {
					String worldName = locationsSection.getString(key + ".world");
					World world = getPlugin().getServer().getWorld(worldName);
					if (world != null) {
						double x = locationsSection.getDouble(key + ".x");
						double y = locationsSection.getDouble(key + ".y");
						double z = locationsSection.getDouble(key + ".z");
						crate.addLocation(new Location(world, x, y, z));
					}
				}
			}

			var rewardsSection = cratesSection.getConfigurationSection(name + ".rewards");
			if (rewardsSection != null) {
				for (String key : rewardsSection.getKeys(false)) {
					String materialName = rewardsSection.getString(key + ".material");
					int amount = rewardsSection.getInt(key + ".amount");
					double weight = rewardsSection.getDouble(key + ".weight");

					Material material = Material.valueOf(materialName.toUpperCase());
					crate.addReward(new CrateReward(material, amount, weight));
				}
			}

			crates.put(name, crate);
		}

		this.getPlugin().getLogger().info("Loaded " + crates.size() + " crates!");
	}

	public void saveCrates() {
		cratesConfig.set("crates", null);
		var cratesSection = cratesConfig.createSection("crates");

		for (var crate : crates.values()) {
			var crateSection = cratesSection.createSection(crate.getName());

			if (crate.getDisplayName() != crate.getName()) {
				crateSection.set("displayName", crate.getDisplayName());
			}

			ConfigurationSection locationsSection = crateSection.createSection("locations");
			int i = 0;
			for (Location location : crate.getLocations()) {
				ConfigurationSection locationSection = locationsSection.createSection(String.valueOf(i++));
				locationSection.set("world", location.getWorld().getName());
				locationSection.set("x", location.getX());
				locationSection.set("y", location.getY());
				locationSection.set("z", location.getZ());
			}

			ConfigurationSection rewardsSection = crateSection.createSection("rewards");
			for (int j = 0; j < crate.getRewards().size(); j++) {
				CrateReward reward = crate.getRewards().get(j);
				ConfigurationSection rewardSection = rewardsSection.createSection(String.valueOf(j));
				rewardSection.set("material", reward.getMaterial().name());
				rewardSection.set("amount", reward.getAmount());
				rewardSection.set("weight", reward.getWeight());
			}
		}

		try {
			cratesConfig.save(cratesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}
}
