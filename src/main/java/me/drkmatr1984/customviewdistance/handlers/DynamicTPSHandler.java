package me.drkmatr1984.customviewdistance.handlers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.drkmatr1984.customviewdistance.CustomViewDistance;
import me.drkmatr1984.customviewdistance.tasks.ServerTPS;

public class DynamicTPSHandler
{
	private CustomViewDistance plugin;
	private int globalViewDistance;
	private int lastTickRate;
    private int tickRate;
	
	public DynamicTPSHandler(CustomViewDistance plugin){
        this.lastTickRate = 20;
		this.plugin = plugin;;
	}
	
	public void calculateViewDistance(){
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new ServerTPS(), 100L, 1L);      
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, () -> {
            tickRate = this.clamp((int)ServerTPS.getTPS(), 0, 20);
            if (tickRate != this.lastTickRate) {
            	double percentOfMaxTickUsed = (tickRate/20);
            	Long percentDistance = (Long)Math.round((plugin.getConfigAccessor().getMaxViewDistance() - plugin.getConfigAccessor().getMinViewDistance()) * percentOfMaxTickUsed);
            	if(plugin.getConfigAccessor().getMinViewDistance() + percentDistance < plugin.getConfigAccessor().getMaxViewDistance())
            	{
            		setGlobalViewDistance(plugin.getConfigAccessor().getMinViewDistance() + (percentDistance.intValue()));
            	}else{
            		setGlobalViewDistance(plugin.getConfigAccessor().getMaxViewDistance());
            	}
                lastTickRate = tickRate;
            }
        }, 0L, 20L);
	}
	
	private int clamp(final int toClamp, final int min, final int max) {
        if (toClamp < min) {
            return min;
        }
        if (toClamp > max) {
            return max;
        }
        return toClamp;
    }

	public int getGlobalViewDistance() {
		return globalViewDistance;
	}

	public void setGlobalViewDistance(int globalViewDistance) {
		this.globalViewDistance = globalViewDistance;
	}
	
	
}