package ml.zer0dasho.corpseimmortal.auxclasses;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

public class Corpse {
	
  Entity hitbox;
  private NPC body;
  private Inventory inventory;

  public Corpse(NPC npc, Inventory inventory){
    body = npc;
    this.inventory = inventory;
  }

  public NPC getBody() {
    return body;
  }

  public void setBody(NPC body) {
    if(body == null){
      Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR: trying to set body to null.");
    }
    this.body = body;
  }

  public Inventory getInventory() {
    return inventory;
  }

  public void setInventory(Inventory inventory) {
    if(inventory == null){
      Bukkit.getConsoleSender().sendMessage("your inventory was CRINGE");
    }
    this.inventory = inventory;
  }
}
