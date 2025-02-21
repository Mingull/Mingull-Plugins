package nl.mingull.core.menuKit_old;

import java.util.function.Consumer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import nl.mingull.core.menuKit_old.interfaces.Icon;

/**
 * Represents a simple menu icon with no metadata or actions.
 * 
 * @deprecated Use {@link AdvancedIcon} instead.
 */
@Deprecated
public class SimpleIcon implements Icon {
	private final ItemStack item;
	private Consumer<Player> playerConsumer;
	private Consumer<InventoryClickEvent> eventConsumer;

	public SimpleIcon(ItemStack item) {
		this(item, p -> {
		});
	}

	public SimpleIcon(ItemStack item, Consumer<Player> playerConsumer) {
		this.item = item;
		this.playerConsumer = playerConsumer;
	}

	@Override
	public ItemStack getItem() {
		return item.clone();
	}

	@Override
	public void click(Player player) {
		if (playerConsumer != null) {
			playerConsumer.accept(player);
		}
	}

	@Override
	public void click(Player player, InventoryClickEvent event) {
		if (playerConsumer != null && eventConsumer != null) {
			playerConsumer.accept(player);
			eventConsumer.accept(event);
		}
	}
}
