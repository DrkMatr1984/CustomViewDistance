package me.drkmatr1984.customviewdistance.configs;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class ConfigAccessor
{
	private CustomViewDistance plugin;
	
	private int minimumViewDistance;
	private int maximumViewDistance;
	public int elytraDistance;
	public boolean dynamicViewDistance = false;
	public int dynamicSetInterval;
	public boolean commandViewDistance = false;
	public boolean permissionViewDistance = false;
	public boolean elytraViewDistance = false;
	
	public ConfigAccessor(CustomViewDistance plugin){
		this.plugin = plugin;
		plugin.saveDefaultConfig();
	}
	
	public boolean loadConfig()
	{
		minimumViewDistance = plugin.getConfig().getInt("general.minViewDistance");
		maximumViewDistance = plugin.getConfig().getInt("general.maxViewDistance");
		elytraViewDistance = plugin.getConfig().getBoolean("elytraViewDistance.enabled");
		elytraDistance = plugin.getConfig().getInt("elytraViewDistance.viewDistance");
		dynamicViewDistance = plugin.getConfig().getBoolean("dynamicViewDistance.enabled");
		dynamicSetInterval = plugin.getConfig().getInt("dynamicViewDistance.interval");
		commandViewDistance = plugin.getConfig().getBoolean("commandViewDistance.enabled");
		permissionViewDistance = plugin.getConfig().getBoolean("permissionViewDistance.enabled");
		return true;
	}
	
	public int getMinViewDistance(){
		return this.minimumViewDistance;
	}
	
	public void setMinViewDistance(int viewDistance){
		this.minimumViewDistance = viewDistance;
	}
	
	public int getMaxViewDistance(){
		return this.maximumViewDistance;
	}
	
	public void setMaxViewDistance(int viewDistance){
		this.maximumViewDistance = viewDistance;
	}
}