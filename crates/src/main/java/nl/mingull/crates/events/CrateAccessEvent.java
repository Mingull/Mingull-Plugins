package nl.mingull.crates.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

import nl.mingull.crates.models.Crate;

public class CrateAccessEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private final Crate crate;
	private final Location location;
	private final Action action;
	private boolean cancelled = false;

	public CrateAccessEvent(Player player, Crate crate, Location location, Action action) {
		this.player = player;
		this.crate = crate;
		this.location = location;
		this.action = action;
	}

	public Player getPlayer() {
		return player;
	}

	public Crate getCrate() {
		return crate;
	}

	public Location getLocation() {
		return location;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
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
