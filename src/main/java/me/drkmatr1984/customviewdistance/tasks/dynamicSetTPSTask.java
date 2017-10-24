package me.drkmatr1984.customviewdistance.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class dynamicSetTPSTask extends BukkitRunnable
{
	private CustomViewDistance plugin;
	
	public dynamicSetTPSTask(CustomViewDistance plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if(!plugin.getServer().getOnlinePlayers().isEmpty()){
			for(Player p : plugin.getServer().getOnlinePlayers()){
				plugin.setViewDistance(p);
			}
		}		
	}
	
}