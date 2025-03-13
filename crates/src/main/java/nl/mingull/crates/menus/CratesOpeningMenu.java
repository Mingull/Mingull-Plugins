package nl.mingull.crates.menus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit.Icon;
import nl.mingull.core.menuKit.Menu;
import nl.mingull.core.menuKit.PlayerMenuController;
import nl.mingull.core.menuKit.Rows;
import nl.mingull.core.utils.Icons;
import nl.mingull.core.utils.Messenger;
import nl.mingull.crates.CratesPlugin;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CratesOpeningMenu extends Menu {

	private final CratesPlugin plugin;
	private final Crate crate;
	private final CrateReward finalReward;
	private int currentTick = 0;
	private final Random random = new Random();

	public CratesOpeningMenu(PlayerMenuController pmc) {
		super(pmc);
		this.crate = pmc.getData("crate", Crate.class);
		this.finalReward = pmc.getData("reward", CrateReward.class);
		this.plugin = CratesPlugin.get();
	}

	@Override
	public Component getTitle() {
		return Messenger.format("<gold>Opening " + crate.getDisplayName() + "...");
	}

	@Override
	public Rows getRows() {
		return Rows.THREE;
	}

	@Override
	public boolean cancelClicks() {
		return true;
	}

	@Override
	public Map<Integer, Icon> getIcons() {
		HashMap<Integer, Icon> icons = new HashMap<>();

		for (int i = 0; i < inventory.getSize(); i++) {
			if (!icons.containsKey(i)) {
				icons.put(i, Icons.GlassPane.StainedBlack);
			}
		}

		List<CrateReward> rewards = crate.getRewards();

		new BukkitRunnable() {
			@Override
			public void run() {
				currentTick++;
				Player player = playerMenuController.getOwner();

				int ticksBetweenUpdates = Math.min(currentTick / 10, 4);
				if (currentTick % (ticksBetweenUpdates + 1) != 0) {
					return;
				}

				if (currentTick < 50) {
					CrateReward randomReward = rewards.get(random.nextInt(rewards.size()));
					inventory.setItem(13, new ItemStack(randomReward.getMaterial(), randomReward.getAmount()));

					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
				} else {
					inventory.setItem(13, new ItemStack(finalReward.getMaterial(), finalReward.getAmount()));
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

					new BukkitRunnable() {
						public void run() {
							player.closeInventory();
							finalReward.giveReward(player);
							player.sendMessage(Messenger.format("<green>You have received "
									+ finalReward.getMaterial().name() + "!"));
						};
					}.runTaskLater(plugin, 30);

					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 1);

		return icons;
	}

}
