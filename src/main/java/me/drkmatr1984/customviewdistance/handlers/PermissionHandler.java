package me.drkmatr1984.customviewdistance.handlers;

import org.bukkit.entity.Player;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class PermissionHandler{
	
	public String commandViewDistancePerm = "customviewdistance.command.viewdistance";
	private CustomViewDistance plugin;
	
	public PermissionHandler(CustomViewDistance plugin){
		this.plugin = plugin;
	}
	
	public int viewDistanceByPermission(Player player){
		int min = 2;
	    int max = 32;
	    for (int distance = max; distance >= min; distance--) {
	        if (player.hasPermission("customviewdistance.perm." + distance)) {	        	
	        	if(distance >= this.plugin.getConfigAccessor().getMaxViewDistance())
	        		distance = this.plugin.getConfigAccessor().getMaxViewDistance();
	            return distance;
	        }
	    }
	    return 0;
	}
}