package nl.mingull.crates.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.menuKit.utils.BorderType;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CratePreviewMenu extends Menu {
	private final CratesPlugin plugin;
	private final Crate crate;

	public CratePreviewMenu(PlayerMenuController pmc) {
		super(pmc);
		border.setBorder(BorderType.FULL, Icons.GlassPane.StainedBlack, getRows(), true);
		this.plugin = CratesPlugin.get();
		this.crate = pmc.getData("crate", Crate.class);
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gold>" + crate.getDisplayName() + " <gray>> <green>Preview");
	}

	@Override
	public Rows getRows() {
		return Rows.SIX;
	}

	@Override
	public boolean cancelClicks() {
		return true;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		Map<Integer, Icon> icons = new HashMap<>();

		var rewards = crate.getRewards();
		List<Integer> validSlots = border.getValidSlots(getInventory());
		for (int i = 0; i < Math.min(rewards.size(), validSlots.size()); i++) {
			CrateReward reward = rewards.get(i);
			Icon icon = new Icon(reward.getMaterial(), reward.getAmount());
			icons.put(validSlots.get(i), icon);
		}

		return icons;

	}

}
