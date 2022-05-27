package ml.zer0dasho.corpseimmortal;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.util.PlayerAnimation;

public class CorpseImmortalAPI {

	private Map<Entity, Corpse> corpses;
	private final NPCRegistry registry;

	CorpseImmortalAPI() {
		this.corpses = new HashMap<>();
		this.registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
	}
	
	public Iterable<Corpse> getCorpses() {
		return corpses.values();
	}
	
	public Corpse getCorpseFromHitbox(LivingEntity e){
		return corpses.get(e);
	}
	
	public void destroyCorpse(Corpse corpse) {
		corpses.remove(corpse.getHitbox());
		corpse.getHitbox().remove();
		corpse.getBody().destroy();
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Used to spawn in the corpse of a player given that player's name and their location.
	 * @param name = players name
	 * @param location = players location
	 */
	public Corpse spawnCorpse(String name, Location location) {
		
		/* Spawn corpse's body */
		NPC npc = registry.createNPC(EntityType.PLAYER, name);
		Inventory corpseInventory = Bukkit.createInventory(null, 45);
		npc.spawn(location);
		
		/* Register corpse and set its inventory */
		Player deadPlayer = Bukkit.getPlayer(name);
		Corpse newCorpse = new Corpse(npc, corpseInventory);
		
		if(deadPlayer != null) //Player is online
			corpseInventory.setContents(deadPlayer.getInventory().getContents().clone());
		
		corpses.put(newCorpse.getHitbox(), newCorpse);

		/* Put corpse into the sleeping position */
		location.setY(-60);
		Bukkit.getOnlinePlayers().forEach(p -> p.sendBlockChange(location, Material.RED_BED, (byte)0));
		
		new BukkitRunnable() {
			int ticks = 0;
			
			@Override
			public void run() {
				if(ticks++ >= 10)
					this.cancel();
				
				if(npc.isSpawned())
					PlayerAnimation.SLEEP.play((Player)npc.getEntity());
			}
		}.runTaskTimer(CorpseImmortal.plugin, 0L, 10L);
		return newCorpse;
	}
	
	public void destroyAll() {
		getCorpses().forEach(this::destroyCorpse);
	}
}
