package me.drkmatr1984.customviewdistance.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class CalcDistanceTask extends BukkitRunnable
{

	private double lastTickRate;
    private double tickRate;
    private CustomViewDistance plugin;
    
    public CalcDistanceTask(CustomViewDistance plugin){
        this.lastTickRate = 20;
		this.plugin = plugin;;
	}
	
	@Override
	public void run() {
		tickRate = this.clamp(ServerTPS.getTPS(), (double)0,(double)20);
        if (tickRate != this.lastTickRate) {
        	double percentOfMaxTickUsed = (((double)tickRate)/((double)20));
        	Long percentDistance = (Long)Math.round(((double)(plugin.getConfigAccessor().getMaxViewDistance() - plugin.getConfigAccessor().getMinViewDistance())) * percentOfMaxTickUsed);
        	if(plugin.getConfigAccessor().getMinViewDistance() + percentDistance.intValue() < plugin.getConfigAccessor().getMaxViewDistance())
        	{     		
            	plugin.getDynamic().setGlobalViewDistance(plugin.getConfigAccessor().getMinViewDistance() + (percentDistance.intValue()));
        	}else{
        		plugin.getDynamic().setGlobalViewDistance(plugin.getConfigAccessor().getMaxViewDistance());
        	}
            lastTickRate = tickRate;
        }
	}
	
	private double clamp(final double toClamp, final double min, final double max) {
        if (toClamp < min) {
            return min;
        }
        if (toClamp > max) {
            return max;
        }
        return toClamp;
    }
	
}