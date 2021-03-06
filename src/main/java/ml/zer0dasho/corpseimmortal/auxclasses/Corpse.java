package ml.zer0dasho.corpseimmortal.auxclasses;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.citizensnpcs.api.npc.NPC;

public class Corpse {

	private UUID id;
	private NPC body;
	private Inventory inventory;
	private LivingEntity hitbox;
  
	public Corpse(NPC npc, UUID id) {
		this(npc, id, Bukkit.createInventory(null, 45));
	}

	public Corpse(NPC npc, UUID id, Inventory inventory){
		this.body = npc;
		this.id = id;
		this.inventory = inventory;
		this.hitbox = initializeHitbox(npc);
	}
  
	private static LivingEntity initializeHitbox(NPC npc){
		Location location = npc.getStoredLocation();
	
		/* Unmovable & uncollidable "hitbox" */
	    LivingEntity hitbox = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.AXOLOTL);
	    npc.getEntity().addPassenger(hitbox);
	    hitbox.setCollidable(false);
    
	    /* Oh, and also invincible. */
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000, 100, true));
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 100, true));
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 100, true));
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1000000, 100, true));
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 100, true));
	    hitbox.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 100, true));
	    return hitbox;
	}

	public NPC getBody() {
		return body;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public LivingEntity getHitbox() {
		return hitbox;
	}
  
	public UUID getId() {
		return id;
	}
}
