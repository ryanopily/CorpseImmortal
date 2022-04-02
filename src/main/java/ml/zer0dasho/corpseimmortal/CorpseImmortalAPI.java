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

	private final NPCRegistry registry;
	private Map<Entity, Corpse> corpses;

	CorpseImmortalAPI() {
		this.registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
		this.corpses = new HashMap<>();
	}
	
	public Iterable<NPC> getCorpses() {
		return registry.sorted();
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Used to spawn in the corpse of a player given that player's name and their location.
	 * @param name = players name
	 * @param location = players location
	 */
	public Corpse spawnCorpse(String name, Location location) {
		NPC npc = registry.createNPC(EntityType.PLAYER, name);
		Inventory corpseInventory = Bukkit.createInventory(null, 45);
		npc.spawn(location);
		
		Corpse newCorpse = new Corpse(npc, corpseInventory);
		Player deadPlayer = Bukkit.getPlayer(name);
		if(deadPlayer != null){ //Player is online
			corpseInventory.setContents(deadPlayer.getInventory().getContents());
		}
		corpses.put(newCorpse.getHitbox(), newCorpse);

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
		getCorpses().forEach(NPC::destroy);
	}

	public Corpse getCorpseFromHitbox(LivingEntity e){
		return corpses.get(e);
	}
}
