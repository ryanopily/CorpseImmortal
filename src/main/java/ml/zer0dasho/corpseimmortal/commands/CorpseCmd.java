package ml.zer0dasho.corpseimmortal.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

import ml.zer0dasho.corpseimmortal.CorpseImmortal;
import net.citizensnpcs.api.npc.NPC;

public class CorpseCmd implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		try {
			if(args[0].equalsIgnoreCase("spawn")) {
				if(args[1].matches("\\w+"))
					CorpseImmortal.API.spawnCorpse(args[1], ((Player)sender).getLocation());
			}

			else if(args[0].matches("remove")) {
				if(args[1].matches("\\d+")) {
					try {
						ImmutableList.copyOf(CorpseImmortal.API.getCorpses())
									 .get(Integer.valueOf(args[1]))
									 .destroy();
					} catch(IndexOutOfBoundsException ex) {
						sender.sendMessage("Invalid corpse!");
					}
				}
					 
				
				else if(args[1].matches("\\w+"))
					Streams.stream(CorpseImmortal.API.getCorpses())
					   .filter(npc -> npc.getName().equals(args[1]))
					   .forEach(NPC::destroy);
			}
		} 
		
		catch(ArrayIndexOutOfBoundsException ex) {
			if(args.length == 0)
				sender.sendMessage("Try /corpse help!");
			else
				sender.sendMessage("Not enough arguments!");
		}
		catch(ClassCastException ex) {
			sender.sendMessage("Only players can use this command!");
		}
		
		return true;
	}
}