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
		elytraViewDistance = Boolean.valueOf(plugin.getConfig().getString("elytraViewDistance.enabled").toUpperCase());
		elytraDistance = plugin.getConfig().getInt("elytraViewDistance.viewDistance");
		if(elytraDistance < 2)
			elytraDistance = 2;
		if(elytraDistance > 32)
			elytraDistance = 32;
		dynamicViewDistance = Boolean.valueOf(plugin.getConfig().getString("dynamicViewDistance.enabled").toUpperCase());
		dynamicSetInterval = plugin.getConfig().getInt("dynamicViewDistance.interval");
		commandViewDistance = Boolean.valueOf(plugin.getConfig().getString("commandViewDistance.enabled").toUpperCase());
		permissionViewDistance = Boolean.valueOf(plugin.getConfig().getString("permissionViewDistance.enabled").toUpperCase());
		return true;
	}
	
	public int getMinViewDistance(){
		if(this.minimumViewDistance < 2)
			return 2;
		if(this.minimumViewDistance > 32)
			return 32;
		return this.minimumViewDistance;
	}
	
	public void setMinViewDistance(int viewDistance){
		if(viewDistance < 2){
			this.minimumViewDistance = 2;
		}else if(viewDistance > 32){
			this.minimumViewDistance = 32;
		}else{
			this.minimumViewDistance = viewDistance;
		}	
	}
	
	public int getMaxViewDistance(){
		if(this.maximumViewDistance > 32)
			return 32;
		if(this.maximumViewDistance < 2)
			return 2;
		return this.maximumViewDistance;
	}
	
	public void setMaxViewDistance(int viewDistance){
		if(viewDistance < 2){
			this.maximumViewDistance = 2;
		}else if(viewDistance > 32){
			this.maximumViewDistance = 32;
		}else{
			this.maximumViewDistance = viewDistance;
		}
	}
}