package ml.zer0dasho.corpseimmortal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.util.PlayerAnimation;

public class CorpseImmortalAPI {

	private final NPCRegistry registry;
	
	
	CorpseImmortalAPI() {
		this.registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());
	}
	
	public Iterable<NPC> getCorpses() {
		return registry.sorted();
	}

	public Corpse getCorpseFromNPC(NPC npc) {
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public NPC spawnCorpse(String name, Location location) {
		NPC npc = registry.createNPC(EntityType.PLAYER, name);
		npc.spawn(location);
		
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
		
		return npc;
	}
	
	public void destroyAll() {
		getCorpses().forEach(NPC::destroy);
	}
}
