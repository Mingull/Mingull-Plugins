package nl.mingull.core.menuKit;

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

public class Icon {
	private final ItemStack item;
	private final ItemMeta meta;
	private Consumer<Player> playerConsumer;
	private PlayerClickEvent eventConsumer;

	/**
	 * Creates an AdvancedIcon with the specified material and a default display name.
	 *
	 * @param material The material of the item.
	 */
	public Icon(Material material) {
		this(material, Component.text("Icon"));
	}

	/**
	 * Creates an AdvancedIcon with the specified material and display name.
	 *
	 * @param material The material of the item.
	 * @param name The display name of the item.
	 */
	public Icon(Material material, Component name) {
		this.item = new ItemStack(material);
		this.meta = this.item.getItemMeta();
		this.setDisplayName(name);
	}

	/**
	 * Sets the display name of the item.
	 *
	 * @param displayName The new display name.
	 */
	public Icon setDisplayName(Component displayName) {
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
	public Icon setMaterial(Material material) {
		Icon newIcon = new Icon(material);
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
	public Icon addLore(Component loreText) {
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
	public Icon addEmptyLore() {
		addLore(Component.empty());
		return this;
	}

	/**
	 * Sets the lore of the item, replacing any existing lore.
	 *
	 * @param loreLines The new lore lines.
	 */
	public Icon setLore(Component... loreLines) {
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
	public <T extends ItemMeta> Icon addMetadata(Consumer<T> consumer, Class<T> metaClass) {
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
	public Icon addMetadata(Consumer<ItemMeta> consumer) {
		consumer.accept(meta);
		item.setItemMeta(meta);
		return this;
	}

	/**
	 * Sets an action that will be executed when the item is clicked.
	 *
	 * @param action The action to execute when clicked.
	 */
	public Icon setAction(Consumer<Player> action) {
		this.playerConsumer = action;
		return this;
	}

	public Icon setAction(PlayerClickEvent action) {
		this.eventConsumer = action::onClick;
		return this;
	}

	/**
	 * Retrieves the ItemStack representation of this icon.
	 *
	 * @return A cloned ItemStack of this icon.
	 */
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
	public void click(Player player) {
		if (playerConsumer != null) {
			playerConsumer.accept(player);
		}
	}

	public void click(Player player, InventoryClickEvent event) {
		if (eventConsumer != null) {
			eventConsumer.onClick(player, event);
		}
	}

	public interface PlayerClickEvent {
		void onClick(Player player, InventoryClickEvent event);
	}
}
