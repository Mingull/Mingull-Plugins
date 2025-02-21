package nl.mingull.core.menuKit_old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;
import nl.mingull.core.menuKit_old.interfaces.Icon;

/**
 * Represents an advanced menu icon with configurable metadata and actions.
 */
public class AdvancedIcon implements Icon {
	private final ItemStack item;
	private final ItemMeta meta;
	private Consumer<Player> playerConsumer;
	private PlayerClickEvent eventConsumer;

	/**
	 * Creates an AdvancedIcon with the specified material and a default display name.
	 *
	 * @param material The material of the item.
	 */
	public AdvancedIcon(Material material) {
		this(material, Component.text("Icon"));
	}

	/**
	 * Creates an AdvancedIcon with the specified material and display name.
	 *
	 * @param material The material of the item.
	 * @param name The display name of the item.
	 */
	public AdvancedIcon(Material material, Component name) {
		this.item = new ItemStack(material);
		this.meta = this.item.getItemMeta();
		this.setDisplayName(name);
	}

	/**
	 * Sets the display name of the item.
	 *
	 * @param displayName The new display name.
	 */
	public AdvancedIcon setDisplayName(Component displayName) {
		meta.displayName(displayName);
		item.setItemMeta(meta);
		return this;
	}

	/**
	 * Retrieves the display name of the item.
	 *
	 * @return The display name of the item.
	 */
	public Component getDisplayName() {
		return meta.displayName();
	}

	/**
	 * Sets the material of the icon while preserving metadata.
	 *
	 * @param material The new material.
	 * @return A new AdvancedIcon instance with the updated material.
	 */
	public AdvancedIcon setMaterial(Material material) {
		AdvancedIcon newIcon = new AdvancedIcon(material);
		newIcon.setDisplayName(getDisplayName());
		newIcon.setLore(getLore().toArray(new Component[0]));
		newIcon.setAction(playerConsumer); // Preserve the click action
		return newIcon;
	}

	/**
	 * Retrieves the material of the icon.
	 * 
	 * @return
	 */
	public Material getMaterial() {
		return item.getType();
	}

	/**
	 * Adds a line of lore to the item.
	 *
	 * @param loreText The text to add to the lore.
	 */
	public AdvancedIcon addLore(Component loreText) {
		if (loreText == null)
			return this;

		List<Component> loreList =
				meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
		loreList.add(loreText);
		meta.lore(loreList);
		item.setItemMeta(meta);
		return this;
	}

	/**
	 * Adds an empty line to the lore.
	 */
	public AdvancedIcon addEmptyLore() {
		addLore(Component.empty());
		return this;
	}

	/**
	 * Sets the lore of the item, replacing any existing lore.
	 *
	 * @param loreLines The new lore lines.
	 */
	public AdvancedIcon setLore(Component... loreLines) {
		meta.lore(Arrays.asList(loreLines));
		item.setItemMeta(meta);
		return this;
	}

	public List<Component> getLore() {
		return meta.hasLore() ? new ArrayList<>(meta.lore()) : new ArrayList<>();
	}

	/**
	 * Applies specific metadata changes to the item.
	 *
	 * @param <T> The type of ItemMeta to modify.
	 * @param consumer The consumer that modifies the meta.
	 * @param metaClass The class type of the metadata.
	 */
	public <T extends ItemMeta> AdvancedIcon addMetadata(Consumer<T> consumer, Class<T> metaClass) {
		if (metaClass.isInstance(meta)) {
			consumer.accept(metaClass.cast(meta));
			item.setItemMeta(meta);
		}
		return this;
	}

	/**
	 * Applies modifications to the item's metadata.
	 *
	 * @param consumer The consumer that modifies the meta.
	 */
	public AdvancedIcon addMetadata(Consumer<ItemMeta> consumer) {
		consumer.accept(meta);
		item.setItemMeta(meta);
		return this;
	}

	/**
	 * Sets an action that will be executed when the item is clicked.
	 *
	 * @param action The action to execute when clicked.
	 */
	public AdvancedIcon setAction(Consumer<Player> action) {
		this.playerConsumer = action;
		return this;
	}

	public AdvancedIcon setAction(PlayerClickEvent action) {
		this.eventConsumer = action::onClick;
		return this;
	}

	/**
	 * Retrieves the ItemStack representation of this icon.
	 *
	 * @return A cloned ItemStack of this icon.
	 */
	@Override
	public ItemStack getItem() {
		return item.clone();
	}

	public PlayerClickEvent getEventConsumer() {
		return eventConsumer;
	}

	/**
	 * Executes the assigned action when clicked by a player.
	 *
	 * @param player The player who clicked the item.
	 */
	@Override
	public void click(Player player) {
		if (playerConsumer != null) {
			playerConsumer.accept(player);
		}
	}

	@Override
	public void click(Player player, InventoryClickEvent event) {
		if (eventConsumer != null) {
			eventConsumer.onClick(player, event);
		}
	}

	public interface PlayerClickEvent {
		void onClick(Player player, InventoryClickEvent event);
	}
}
