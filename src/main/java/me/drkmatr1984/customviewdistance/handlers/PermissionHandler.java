package me.drkmatr1984.customviewdistance.handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class PermissionHandler{
	
	public String commandViewDistancePerm = "customviewdistance.command.viewdistance";
	public String viewDistanceBypass = "customviewdistance.admin.bypass";
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
	    return -1;
	}
	
	public boolean hasViewDistancePerm(Player player, int viewDistance){
		List<Integer> perms = new ArrayList<Integer>();
		int min = 2;
	    int max = 32;
	    for (int distance = max; distance >= min; distance--) {
	        if (player.hasPermission("customviewdistance.perm." + distance)) {	        	
	        	if(distance >= this.plugin.getConfigAccessor().getMaxViewDistance())
	        		distance = this.plugin.getConfigAccessor().getMaxViewDistance();
	            perms.add(distance);
	        }
	    }
	    if(!perms.isEmpty()){
	    	if(perms.contains(viewDistance)){
	    		return true;
	    	}
	    }
	    return false;
	}
}