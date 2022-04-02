package ml.zer0dasho.corpseimmortal.events;

import ml.zer0dasho.corpseimmortal.CorpseImmortalAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import net.citizensnpcs.api.event.NPCRightClickEvent;

public class CorpseClickListener implements Listener {
  @EventHandler
  public void corpseClickWrapper(NPCRightClickEvent e){
    if(CorpseImmortal.API.getCorpseFromNPC(e.getNPC()) != null) {
        e.getClicker().sendMessage(e.getNPC().getName() + " is dead!");
        CorpseClickEvent corpseClickEvent =
            new CorpseClickEvent(CorpseImmortal.API.getCorpseFromNPC(e.getNPC()), e.getClicker());
        Bukkit.getPluginManager().callEvent(corpseClickEvent);
    }
  }

  @EventHandler
  public void openInventory(CorpseClickEvent e){
    e.getClicker().openInventory(e.getCorpse().getInventory());
  }
}