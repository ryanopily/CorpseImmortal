package ml.zer0dasho.corpseimmortal.events;

import net.citizensnpcs.api.event.NPCClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CorpseClickListener implements Listener {
  @EventHandler
  public void openInventory(NPCClickEvent e){
    e.getClicker().sendMessage(e.getNPC().getName());
  }
}
