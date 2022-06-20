package ml.zer0dasho.corpseimmortal.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;

public class CorpseClickListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		CorpseImmortal.API().spawnCorpse(e.getEntity().getName(), e.getEntity().getLocation());
		e.getDrops().clear();
	}
	
	  @EventHandler
	  public void onCorpseClick(PlayerInteractAtEntityEvent e) {
	    if(e.getRightClicked() instanceof LivingEntity) {
	      Corpse clickedCorpse = CorpseImmortal.API().getCorpseFromHitbox((LivingEntity) e.getRightClicked());
	
	      if (clickedCorpse != null) {
	        CorpseClickEvent corpseClickEvent = new CorpseClickEvent(clickedCorpse, e.getPlayer());
	        Bukkit.getPluginManager().callEvent(corpseClickEvent);
	      }
	    }
	  }
  
	  @EventHandler 
	  public void onHitboxDamage(EntityDamageEvent e) {
		  if(e.getEntity() instanceof LivingEntity) {
			  Corpse corpse = CorpseImmortal.API().getCorpseFromHitbox((LivingEntity)e.getEntity());
			  e.setCancelled(corpse != null);
		  }
	  }
  
	  @EventHandler
	  public void openCorpseInventory(CorpseClickEvent e){
		CorpseOpenInventoryEvent ev = new CorpseOpenInventoryEvent(e.getCorpse(), e.getClicker());
		Bukkit.getPluginManager().callEvent(ev);
		
		if(!ev.isCancelled())
			e.getClicker().openInventory(e.getCorpse().getInventory());
	  }
  
	  @EventHandler
	  public void closeCorpseInventory(InventoryCloseEvent e) {	
		  if(e.getInventory().isEmpty()) {
			  Corpse corpse = CorpseImmortal.API().getCorpseFromInventory(e.getInventory());
			 
			  if(corpse != null)
				  CorpseImmortal.API().destroyCorpse(corpse);
		  }
	  }
}