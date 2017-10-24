package me.drkmatr1984.customviewdistance.handlers;

import org.bukkit.Bukkit;
import me.drkmatr1984.customviewdistance.CustomViewDistance;
import me.drkmatr1984.customviewdistance.tasks.CalcDistanceTask;
import me.drkmatr1984.customviewdistance.tasks.ServerTPS;

public class DynamicTPSHandler
{
	private CustomViewDistance plugin;
	private int globalViewDistance;
	
	
	public DynamicTPSHandler(CustomViewDistance plugin){
		this.plugin = plugin;
		this.globalViewDistance = -1;
	}
	
	public void calculateViewDistance(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, (Runnable) new ServerTPS(), 100L, 1L);      
        new CalcDistanceTask(plugin).runTaskTimer(plugin, 0L, 20L);
	}

	public int getGlobalViewDistance() {
		return globalViewDistance;
	}

	public void setGlobalViewDistance(int globalViewDistance) {
		this.globalViewDistance = globalViewDistance;
	}
	
	
}