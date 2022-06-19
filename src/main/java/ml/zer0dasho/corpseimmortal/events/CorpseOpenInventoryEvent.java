package ml.zer0dasho.corpseimmortal.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;

public class CorpseOpenInventoryEvent extends Event implements Cancellable {

	private Corpse corpse;
	private Player clicker;
	private boolean cancel;

	public CorpseOpenInventoryEvent(Corpse corpse, Player clicker) {
		this.corpse = corpse;
		this.clicker = clicker;
	}

	public Corpse getCorpse() {
		return corpse;
	}

	public Player getClicker() {
		return clicker;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	private static final HandlerList HANDLERS = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}