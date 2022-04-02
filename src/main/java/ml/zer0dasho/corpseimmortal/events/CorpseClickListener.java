package ml.zer0dasho.corpseimmortal.events;

import ml.zer0dasho.corpseimmortal.CorpseImmortalAPI;
import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CorpseClickListener implements Listener {
  @EventHandler
  public void corpseClickWrapper(PlayerInteractEntityEvent e){
    if(e.getRightClicked() instanceof LivingEntity) {
      Corpse clickedCorpse =
          CorpseImmortal.API.getCorpseFromHitbox((LivingEntity) e.getRightClicked());

      if (clickedCorpse != null) {
        e.getPlayer().sendMessage(clickedCorpse.getBody().getName() + " is dead!");
        CorpseClickEvent corpseClickEvent =
            new CorpseClickEvent(clickedCorpse, e.getPlayer());
        Bukkit.getPluginManager().callEvent(corpseClickEvent);
      }
    }
  }

  @EventHandler
  public void openInventory(CorpseClickEvent e){
    e.getClicker().openInventory(e.getCorpse().getInventory());
  }
}