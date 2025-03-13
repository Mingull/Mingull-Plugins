package nl.mingull.crates.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
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
import nl.mingull.crates.managers.CrateManager;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CrateRewardMenu extends Menu {
	private final CratesPlugin plugin;
	private final Crate crate;

	public CrateRewardMenu(PlayerMenuController pmc) {
		super(pmc);
		border.setBorder(BorderType.FULL, Icons.GlassPane.StainedBlack, getRows(), true);
		border.setIcon(49, Icons.createHeadIcon(
				"http://textures.minecraft.net/texture/a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6",
				"<green>Save rewards", "<gray>Click to save items above",
				"<gray>as the rewards for this crate").setAction((p, e) -> {
					e.setCancelled(true);
					saveRewards();
					p.closeInventory();
				}));
		setBackIcon(45);
		this.plugin = JavaPlugin.getPlugin(CratesPlugin.class);
		this.crate = pmc.getData("crate", Crate.class);
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gold>" + crate.getDisplayName() + " <gray>> <green>Rewards");
	}

	@Override
	public Rows getRows() {
		return Rows.SIX;
	}

	@Override
	public boolean cancelClicks() {
		return false;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		Map<Integer, Icon> icons = new HashMap<>();
		if (crate != null) {
			var rewards = crate.getRewards();
			List<Integer> validSlots = border.getValidSlots(getInventory());
			for (int i = 0; i < Math.min(rewards.size(), validSlots.size()); i++) {
				CrateReward reward = rewards.get(i);
				Icon rewardIcon = new Icon(reward.getMaterial(), reward.getAmount());
				icons.put(validSlots.get(i), rewardIcon);
			}
		}
		return icons;
	}

	private void saveRewards() {
		List<CrateReward> rewards = new ArrayList<>();
		for (int i = 0; i < getInventory().getSize(); i++) {
			if (border.getBorderSlots().contains(i)) {
				continue;
			}

			ItemStack item = getInventory().getItem(i);
			if (item == null) {
				continue;
			}
			Icon icon = new Icon(item.getType(), item.displayName(), item.getAmount());
			if (icon != null) {
				rewards.add(new CrateReward(icon.getMaterial(), icon.getAmount(), 10));
			}
		}

		if (crate != null) {
			crate.getRewards().clear();
			rewards.forEach(crate::addReward);
			plugin.getManager(CrateManager.class).saveCrates();
			player.sendMessage(Messenger.format("<green>Rewards saved!"));
		}
	}
}
