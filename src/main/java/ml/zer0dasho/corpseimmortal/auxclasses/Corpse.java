package ml.zer0dasho.corpseimmortal.auxclasses;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Corpse {
	
  private NPC body;
  private Inventory inventory;
  private LivingEntity hitbox;

  public Corpse(NPC npc, Inventory inventory){
    this.body = npc;
    this.inventory = inventory;
    initializeHitbox(npc);
  }
  
  public void initializeHitbox(NPC npc){
    hitbox = (LivingEntity) npc.getStoredLocation().getWorld().spawnEntity(npc.getStoredLocation(), EntityType.AXOLOTL);
    npc.getEntity().addPassenger(hitbox);
    hitbox.setCollidable(false);
    
    getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 100, true));
    getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 100, true));
    getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 100, true));
    getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000000, 100, true));
    getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 100, true));
    //getHitbox().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 100, true));
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
      Bukkit.getConsoleSender().sendMessage("Corpse.setInventory() received a null inventory");
    }
    this.inventory = inventory;
  }

  public LivingEntity getHitbox() {
    return hitbox;
  }
}
