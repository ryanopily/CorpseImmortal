package ml.zer0dasho.corpseimmortal.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class CorpseClickListener implements Listener {
	
  @EventHandler
  public void openInventory(NPCRightClickEvent e){
	if(CorpseImmortal.API.getCorpseFromNPC(e.getNPC()) != null) {  
	    e.getClicker().sendMessage(e.getNPC().getName() + " is dead!");	
	}
  }
  
}