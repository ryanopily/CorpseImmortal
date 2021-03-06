package ml.zer0dasho.corpseimmortal.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;

public class CorpseClickEvent extends Event {

	private Corpse corpse;
	private Player clicker;

	public CorpseClickEvent(Corpse corpse, Player clicker) {
		this.corpse = corpse;
		this.clicker = clicker;
	}

  	public Corpse getCorpse() {
	  return corpse;
  	}

  	public Player getClicker() {
	  return clicker;
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