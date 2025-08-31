package nl.mingull.crates.managers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import nl.mingull.core.managerKit.Manager;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;

public class KeyManager extends Manager {
	private final NamespacedKey crateKey;

	public KeyManager(CratesPlugin plugin) {
		super(plugin);
		this.crateKey = new NamespacedKey(plugin, "crate_key");
	}

	public Icon getKey(Crate crate) {
		Icon key = new Icon(Material.TRIAL_KEY,
				Messenger.format("<gold>" + crate.getDisplayName() + "<gray> Key"));
		key.addMetadata((meta) -> {
			meta.getPersistentDataContainer().set(crateKey, PersistentDataType.STRING,
					crate.getName());
		});
		return key;
	}

	public boolean isKeyForCrate(Icon icon, Crate crate) {
		if (icon.getMaterial() == Material.TRIAL_KEY && icon.hasMetadata() && icon.getMetadata()
				.getPersistentDataContainer().has(crateKey, PersistentDataType.STRING)) {
			return icon.getMetadata().getPersistentDataContainer()
					.get(crateKey, PersistentDataType.STRING).equals(crate.getName());
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public CratesPlugin getPlugin() {
		return (CratesPlugin) super.getPlugin();
	}
}
