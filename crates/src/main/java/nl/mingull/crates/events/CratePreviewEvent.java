package nl.mingull.crates.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;
import nl.mingull.crates.models.Crate;

public class CratePreviewEvent extends Event implements Cancellable {
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private final Player player;
	private final Crate crate;
	private final Location location;
	private boolean cancelled = false;


	public CratePreviewEvent(Player player, Crate crate, Location location) {
		this.player = player;
		this.crate = crate;
		this.location = location;
	}

	public Crate getCrate() {
		return crate;
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLocation() {
		return location;
	}

	@NotNull
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
