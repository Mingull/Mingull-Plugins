package nl.mingull.crates.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import nl.mingull.crates.models.Crate;
import nl.mingull.crates.models.CrateReward;

public class CrateOpeningEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final Player player;
	private final Crate crate;
	private final CrateReward reward;
	private boolean cancelled = false;

	public CrateOpeningEvent(Player player, Crate crate, CrateReward reward) {
		this.player = player;
		this.crate = crate;
		this.reward = reward;
	}

	public Player getPlayer() {
		return player;
	}

	public Crate getCrate() {
		return crate;
	}

	public CrateReward getReward() {
		return reward;
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
