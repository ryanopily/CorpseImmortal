package ml.zer0dasho.corpsereborn;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.util.PlayerAnimation;

public class Main extends JavaPlugin implements Listener {

	public static NPCRegistry registry;
	
	@Override
	public void onEnable() {
		Main.registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

		new BukkitRunnable() {
			@Override
			public void run() {
				registry.sorted().forEach(npc -> {
					Bukkit.getOnlinePlayers().forEach(p -> {
						p.sendBlockChange(npc.getEntity().getLocation().add(0, -20, 0), Material.RED_BED, (byte)0);
					});
					PlayerAnimation.SLEEP.play((Player)npc.getEntity());
				});
			}
		}.runTaskTimer(this, 0L, 20L);
		
		this.getCommand("corpse").setExecutor(this);
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		try {
			if(args[0].equalsIgnoreCase("spawn")) {
				spawn((Player)sender, args[1]);
			}
			
			else if(args[0].equalsIgnoreCase("remove")) {
				remove(args[1]);
			}
		} 
		
		catch(ArrayIndexOutOfBoundsException ex) {
			sender.sendMessage("Not enough arguments!");
		}
		
		catch(ClassCastException ex) {
			sender.sendMessage("Only players can use this command!");
		}
		
		return true;
	}

	private void spawn(Player player, String name) {
		Main.registry.createNPC(EntityType.PLAYER, name).spawn(player.getLocation());
	}
	
	private void remove(String name) {
		Main.registry.sorted().forEach(npc -> {
			if(npc.getName().equalsIgnoreCase(name)) {
				npc.destroy();
			}
		});
	}
	
	@Override
	public void onDisable() {
		registry.sorted().forEach(npc -> npc.destroy());
	}
}