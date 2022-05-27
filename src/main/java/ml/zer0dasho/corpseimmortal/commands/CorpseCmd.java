package ml.zer0dasho.corpseimmortal.commands;

import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import ml.zer0dasho.corpseimmortal.Utils;
import ml.zer0dasho.corpseimmortal.auxclasses.Corpse;

public class CorpseCmd implements CommandExecutor {
	
	private void spawn(CommandSender sender, String...args) {
		if(args[1].matches("\\w+")) {
			CorpseImmortal.API.spawnCorpse(args[1], ((Player)sender).getLocation());
			sender.sendMessage(Utils.color("&aSuccessfully spawned " + args[1] + "'s corpse!"));
		} 
		
		else
			sender.sendMessage(Utils.color("&cTry /corpse spawn [name]"));
	}
	
	private void list(CommandSender sender, String...args) {
		
		int index = 0, i = 0;
		if(args[1].matches("\\d+"))
			index = Integer.valueOf(args[1]) * 10;
		
		List<Corpse> corpses = List.copyOf((Collection<Corpse>)CorpseImmortal.API.getCorpses());
		sender.sendMessage(Utils.color("&eCorpse List"));
		for(; index < corpses.size(); index++) {
			sender.sendMessage(Utils.color("&e" + ++i + ") " + corpses.get(index).getBody().getName()));
		}
	}
	
	private void remove(CommandSender sender, String...args) {
		if(args[1].matches("\\d+")) {
			try {
				Corpse corpse = ImmutableList.copyOf(CorpseImmortal.API.getCorpses()).get(Integer.valueOf(args[1]));
				CorpseImmortal.API.destroyCorpse(corpse);	
				sender.sendMessage(Utils.color("&aSuccessfully destroyed " + corpse.getBody().getName() + "'s corpse!"));
			} 
			
			catch(IndexOutOfBoundsException ex) {
				sender.sendMessage(Utils.color("&cInvalid corpse!"));
			}
		}
			 
		else if(args[1].matches("\\w+")) {
			Streams.stream(CorpseImmortal.API.getCorpses())
			   .filter(c -> c.getBody().getName().equals(args[1]))
			   .forEach(CorpseImmortal.API::destroyCorpse);
			sender.sendMessage(Utils.color("&aSuccessfully destroyed " + args[1] + "s' corpses!"));
		}
		
		else 
			sender.sendMessage(Utils.color("7cTry /corpse remove [name/index]!"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			switch(args[0]) {
				case "spawn":
					spawn(sender, args);
					break;
				case "remove":
					remove(sender, args);
					break;
				case "list":
					list(sender, args);
					break;
				default:
					sender.sendMessage(Utils.color("&cTry /corpse help!"));
					break;
			}
		} 
		
		catch(ArrayIndexOutOfBoundsException ex) {
			if(args.length == 0)
				sender.sendMessage(Utils.color("&cTry /corpse help!"));
			
			else
				sender.sendMessage(Utils.color("&cNot enough arguments!"));
		} 
		
		catch(ClassCastException ex) {
			sender.sendMessage(Utils.color("&cOnly players can use this command!"));
		}
		
		return true;
	}
}