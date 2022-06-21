package ml.zer0dasho.corpseimmortal;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ml.zer0dasho.corpseimmortal.events.CorpseClickListener;

public class CorpseImmortal extends JavaPlugin implements Listener {

	//comment to test jenkins
	@Override
	public void onEnable() {
		CorpseImmortal.plugin = this;
		CorpseImmortal.API = new CorpseImmortalAPI();
		Bukkit.getPluginManager().registerEvents(new CorpseClickListener(), this);
	}
	
	@Override
	public void onDisable() {
		CorpseImmortal.API.destroyAll();
	}
	
	
	private static CorpseImmortal plugin;
	private static CorpseImmortalAPI API;
	
	public static CorpseImmortal plugin() {
		return CorpseImmortal.plugin;
	}
	
	public static CorpseImmortalAPI API() {
		return CorpseImmortal.API;
	}
}