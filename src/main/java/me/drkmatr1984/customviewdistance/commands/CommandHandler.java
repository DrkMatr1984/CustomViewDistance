package me.drkmatr1984.customviewdistance.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.drkmatr1984.customviewdistance.CustomViewDistance;
import me.drkmatr1984.customviewdistance.handlers.PermissionHandler;


public class CommandHandler implements CommandExecutor
{
	private CustomViewDistance plugin;
	private PermissionHandler pH;
	
	public CommandHandler(CustomViewDistance plugin){
		this.plugin = plugin;
		this.pH = plugin.getPermHandler();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		try{
			if (cmd.getName().equalsIgnoreCase("viewdistance")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Must be a player to use this command");
				}else{
					Player player = (Player) sender;
					if(player.hasPermission(pH.commandViewDistancePerm)){
						if ((args.length == 0) || (args.equals(null)))
					    {
							sender.sendMessage("ViewDistance requires an Integer between " + plugin.getConfigAccessor().getMinViewDistance() + " and " + plugin.getConfigAccessor().getMaxViewDistance());
					    }
						if(args.length == 1){
							if(isInteger(args[0])){
								if(this.pH.hasViewDistancePerm(player, Integer.parseInt(args[0]))){
									if(Integer.parseInt(args[0]) <= plugin.getConfigAccessor().getMaxViewDistance() && Integer.parseInt(args[0]) >= plugin.getConfigAccessor().getMinViewDistance())
									{
										if(plugin.getDataHandler().playerSetDistance.containsKey(player.getUniqueId())){
											plugin.getDataHandler().playerSetDistance.remove(player.getUniqueId());
										}
										plugin.getDataHandler().playerSetDistance.put(player.getUniqueId(), Integer.parseInt(args[0]));
										plugin.setViewDistance(player);				
									}else{
										sender.sendMessage("ViewDistance requires an Integer between " + plugin.getConfigAccessor().getMinViewDistance() + " and " + plugin.getConfigAccessor().getMaxViewDistance());
									}
								}else{
									player.sendMessage("You don't have permission");
								}
							}						
					    }else if(args.length > 1){
					    	sender.sendMessage("ViewDistance requires an Integer between " + plugin.getConfigAccessor().getMinViewDistance() + " and " + plugin.getConfigAccessor().getMaxViewDistance());
					    }
					}else{
						player.sendMessage("You don't have permission");
					}
					
				}			
			}
		}catch (Exception e) {
			sender.sendMessage("Exception");
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