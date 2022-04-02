package ml.zer0dasho.corpseimmortal;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ml.zer0dasho.corpseimmortal.commands.CorpseCmd;
import ml.zer0dasho.corpseimmortal.events.CorpseClickListener;

public class CorpseImmortal extends JavaPlugin implements Listener {
	
	public static CorpseImmortal plugin;
	public static CorpseImmortalAPI API;
	public static CorpseClickListener events;
	
	@Override
	public void onEnable() {
		CorpseImmortal.plugin = this;
		CorpseImmortal.API = new CorpseImmortalAPI();
		CorpseImmortal.events = new CorpseClickListener();
		
		CorpseImmortal.API.spawnCorpse("Timbome", Bukkit.getWorlds().get(0).getSpawnLocation());
		
		getCommand("corpse").setExecutor(new CorpseCmd());
		Bukkit.getPluginManager().registerEvents(events, this);
	}
	
	@Override
	public void onDisable() {
		CorpseImmortal.API.destroyAll();
	}
	
	
}