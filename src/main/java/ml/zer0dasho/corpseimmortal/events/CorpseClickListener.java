package ml.zer0dasho.corpseimmortal.events;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import com.google.common.collect.ImmutableList;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;

public class CorpseClickListener implements Listener {
	
  @EventHandler
  public void corpseClickWrapper(PlayerInteractAtEntityEvent e) {
    if(e.getRightClicked() instanceof LivingEntity) {

      Corpse clickedCorpse = CorpseImmortal.API.getCorpseFromHitbox((LivingEntity) e.getRightClicked());

      if (clickedCorpse != null) {
        //e.getPlayer().sendMessage(clickedCorpse.getBody().getName() + " is dead!");
        CorpseClickEvent corpseClickEvent = new CorpseClickEvent(clickedCorpse, e.getPlayer());
        Bukkit.getPluginManager().callEvent(corpseClickEvent);
      }
    }
  }
  
  @EventHandler 
  public void hitboxDamage(EntityDamageEvent e) {
	  if(!(e.getEntity() instanceof LivingEntity))
		  return;
	  
	  Corpse corpse = CorpseImmortal.API.getCorpseFromHitbox((LivingEntity)e.getEntity());
	  if(corpse != null)
		  e.setCancelled(true);
  }
  
  @EventHandler
  public void openInventory(CorpseClickEvent e){
	CorpseOpenInventoryEvent ev = new CorpseOpenInventoryEvent(e.getCorpse(), e.getClicker());
	Bukkit.getPluginManager().callEvent(ev);
	
	if(!ev.isCancelled())
		e.getClicker().openInventory(e.getCorpse().getInventory());
  }
  
  @EventHandler
  public void closeInventory(InventoryCloseEvent e) {	
	  ImmutableList.copyOf(CorpseImmortal.API.getCorpses()).stream()
	  	.filter(c -> c.getInventory().equals(e.getInventory()) && c.getInventory().isEmpty())	  	
	  	.forEach(c -> {
	  		System.out.println(c.getInventory().isEmpty() );
	  		System.out.println(Arrays.toString(c.getInventory().getContents()));
	  		CorpseImmortal.API.destroyCorpse(c);
	  	});
  }
  
}