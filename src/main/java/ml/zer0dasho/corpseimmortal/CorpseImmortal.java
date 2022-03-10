package ml.zer0dasho.corpseimmortal;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ml.zer0dasho.corpseimmortal.commands.CorpseCmd;

public class CorpseImmortal extends JavaPlugin implements Listener {
	
	public static CorpseImmortal plugin;
	public static CorpseImmortalAPI API;
	
	@Override
	public void onEnable() {
		CorpseImmortal.plugin = this;
		CorpseImmortal.API = new CorpseImmortalAPI();
		
		CorpseImmortal.API.spawnCorpse("Timbome", Bukkit.getWorlds().get(0).getSpawnLocation());
		
		getCommand("corpse").setExecutor(new CorpseCmd());
	}
	
	@Override
	public void onDisable() {
		CorpseImmortal.API.destroyAll();
	}
}