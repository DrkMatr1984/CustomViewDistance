package me.drkmatr1984.customviewdistance.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.drkmatr1984.customviewdistance.CustomViewDistance;


public class CommandHandler implements CommandExecutor
{
	private CustomViewDistance plugin;
	
	public CommandHandler(CustomViewDistance plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("viewdistance")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Must be a player to use this command");
				}else{
					Player player = (Player) sender;
					if ((args.length == 0) || (args.equals(null)))
				    {
						sender.sendMessage("ViewDistance requires an Integer between " + plugin.getConfigAccessor().getMinViewDistance() + " and " + plugin.getConfigAccessor().getMaxViewDistance());
				    }else if(args.length > 0){
				    	player.sendMessage(args[0]);
						if(isInteger(args[0])){
							player.setViewDistance(Integer.parseInt(args[0]));
							plugin.getDataHandler().playerSetDistance.put(player.getUniqueId(), Integer.parseInt(args[0]));
						}						
				    }
				}			
			}
		}catch (Exception e) {
			
		}
		return false;
		
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}