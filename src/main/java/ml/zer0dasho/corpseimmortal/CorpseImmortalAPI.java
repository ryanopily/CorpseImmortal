package ml.zer0dasho.corpseimmortal;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.ImmutableList;

import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.util.PlayerAnimation;

public class CorpseImmortalAPI {

	private Map<Entity, Corpse> hitboxes;
	private Map<Inventory, Corpse> inventories;
	
	private final NPCRegistry registry;

	CorpseImmortalAPI() {
		this.hitboxes = new HashMap<>();
		this.inventories = new HashMap<>();
		this.registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
	}
	
	/**
	 * Used to spawn in the corpse of a player given that player's name and their location.
	 * 
	 * @param name = players name
	 * @param location = players location
	 */
	public Corpse spawnCorpse(String name, Location location) {
		
		/* Spawn corpse's body */
		NPC npc = registry.createNPC(EntityType.PLAYER, name);
		npc.spawn(location);

		/* Register corpse and set its inventory */
		@SuppressWarnings("deprecation")
		OfflinePlayer deadPlayer = Bukkit.getOfflinePlayer(name);
		Corpse corpse = new Corpse(npc, deadPlayer.getUniqueId());
		
		if(deadPlayer.isOnline())
			corpse.getInventory().setContents(((Player)deadPlayer).getInventory().getContents().clone());
		
		hitboxes.put(corpse.getHitbox(), corpse);
		inventories.put(corpse.getInventory(), corpse);

		/* Put corpse into the sleeping position */
		Bukkit.getOnlinePlayers().forEach(p -> p.sendBlockChange(location, Material.RED_BED.createBlockData()));
		
		new BukkitRunnable() {
			
			int ticks = 0;
			
			@Override
			public void run() {
				if(ticks++ >= 10)
					this.cancel();
				
				if(npc.isSpawned())
					PlayerAnimation.SLEEP.play((Player)npc.getEntity());
			}
		}.runTaskTimer(CorpseImmortal.plugin(), 0L, 10L);
		
		return corpse;
	}
	
	/**
	 * Destroy a corpse, including its hitbox and body.
	 * 
	 * @param corpse
	 */
	public void destroyCorpse(Corpse corpse) {
		hitboxes.remove(corpse.getHitbox());
		inventories.remove(corpse.getInventory());
		corpse.getHitbox().remove();
		corpse.getBody().destroy();
	}
	
	/**
	 * Destroy all corpses, including hitboxes and bodies.
	 */
	public void destroyAll() {
		ImmutableList.copyOf(getCorpses()).forEach(this::destroyCorpse);
	}
	
	/**
	 * Get all registered corpses.
	 * 
	 * @return
	 */
	public Iterable<Corpse> getCorpses() {
		return hitboxes.values();
	}
	
	/**
	 * Get a corpse given its hitbox.
	 * 
	 * @param e
	 * @return
	 */
	public Corpse getCorpseFromHitbox(LivingEntity e){
		return hitboxes.get(e);
	}
	
	/**
	 * Get a corpse given its inventory.
	 * @param i
	 * @return
	 */
	public Corpse getCorpseFromInventory(Inventory i) {
		return inventories.get(i);
	}
}
