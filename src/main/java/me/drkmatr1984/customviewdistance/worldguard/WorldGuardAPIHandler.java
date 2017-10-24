package me.drkmatr1984.customviewdistance.worldguard;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.IllegalPluginAccessException;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.IntegerFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class WorldGuardAPIHandler
{
	private WorldGuardPlugin worldGuard;
	FlagRegistry registry;
	private CustomViewDistance plugin;
	private boolean enabled;
	public static final IntegerFlag VIEW_DISTANCE = new IntegerFlag("view-distance");
	
	public WorldGuardAPIHandler(Plugin worldguard, CustomViewDistance plugin) throws IllegalPluginAccessException{
		this.plugin = plugin;
		this.enabled = wgEnabled(worldguard);
		if(this.enabled){
			this.enabled = registerFlag(this.worldGuard);
		}
		if(!this.enabled){
			throw new IllegalPluginAccessException();
		}
	}
	
	public boolean wgEnabled(Plugin wg){
		if (wg == null || !(wg instanceof WorldGuardPlugin)) {
	        return false;
	    }
		this.worldGuard = (WorldGuardPlugin) wg;
		return true;
	}
	
	public boolean registerFlag(WorldGuardPlugin wg){
		this.registry = wg.getFlagRegistry();
		try {
	        // register our flag with the registry
	        registry.register(VIEW_DISTANCE);
	        return true;
	    } catch (FlagConflictException e) {
	        this.plugin.getServer().getLogger().info("Can't register view-distance flag as another plugin has already registered a flag with this name");
	        return false;
	    }
	}
	
	public boolean isEnabled(){
		return this.enabled;
	}

	public WorldGuardPlugin getWorldGuard() {
		return this.worldGuard;
	}

	public CustomViewDistance getPlugin() {
		return this.plugin;
	}
}