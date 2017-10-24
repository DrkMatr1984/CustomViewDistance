package me.drkmatr1984.customviewdistance;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.drkmatr1984.customviewdistance.commands.CommandHandler;
import me.drkmatr1984.customviewdistance.configs.ConfigAccessor;
import me.drkmatr1984.customviewdistance.configs.PlayerData;
import me.drkmatr1984.customviewdistance.handlers.DynamicTPSHandler;
import me.drkmatr1984.customviewdistance.handlers.PermissionHandler;
import me.drkmatr1984.customviewdistance.listeners.EventListener;
import me.drkmatr1984.customviewdistance.tasks.dynamicSetTPSTask;
import me.drkmatr1984.customviewdistance.worldguard.WorldGuardAPIHandler;

public class CustomViewDistance extends JavaPlugin implements Listener {
	
	private ConfigAccessor config;
	private PlayerData players;
	private DynamicTPSHandler dynamic;
	private PermissionHandler permission;
	private PluginManager pm;
	private WorldGuardAPIHandler wghandler;
	private boolean isWGEnabled = false;
	private Logger log;
	
    @Override
    public void onEnable() {
    	this.pm = this.getServer().getPluginManager();
    	this.log = this.getServer().getLogger();
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        } catch (ClassNotFoundException e) {
            error("&4#&4#############################################################################&4#");
            error("&4#&c                 This plugin only works on Paper servers!                    &4#");
            error("&4#&4                                                                             &4#");
            error("&4#&4     To prevent server crashes and other undesired behavior this plugin      &4#");
            error("&4#&4       is disabling itself from running. Please remove the plugin jar        &4#");
            error("&4#&4             from your plugins directory to free up some memory.             &4#");
            error("&4#&4#############################################################################&4#");
            this.pm.disablePlugin(this);
            return;
        }
        config = new ConfigAccessor(this);
        config.loadConfig();
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        if(this.getConfigAccessor().getMaxViewDistance() > this.getServer().getViewDistance())
			this.getConfigAccessor().setMaxViewDistance(this.getServer().getViewDistance());
        permission = new PermissionHandler(this);         
        if(config.commandViewDistance){
        	players = new PlayerData(this);
        	players.initLists();
        	getCommand("viewdistance").setExecutor(new CommandHandler(this));
        }        
        if(config.dynamicViewDistance){
        	dynamic = new DynamicTPSHandler(this);
        	this.getDynamic().setGlobalViewDistance(this.getConfigAccessor().getMaxViewDistance());
        	this.getDynamic().calculateViewDistance();
        	new dynamicSetTPSTask(this).runTaskTimer(this, 20, config.dynamicSetInterval*20);
        }
        if(pm.isPluginEnabled("WorldGuard")){
        	Plugin wg = pm.getPlugin("WorldGuard");
        	try{
        		this.wghandler = new WorldGuardAPIHandler(wg, this);
        		this.isWGEnabled = true;
        		log.info("WorldGuard view-distance flag Enabled");
        	}catch(IllegalPluginAccessException e){
        		this.isWGEnabled = false;
        		log.info("WorldGuard view-distance couldn't be successfully Enabled");
        	}
        	
        }
    }
    
    @Override
    public void onDisable() {
    	if(config.commandViewDistance){
    		players.saveUserList();
    	}
    }
    
    public boolean isWGEnaled(){
    	return this.isWGEnabled;
    }
    
    public WorldGuardAPIHandler getWGHandler(){
    	if(this.isWGEnabled){
    		return this.wghandler;
    	}
    	return null;
    }

	public ConfigAccessor getConfigAccessor() {
		return config;
	}
	
	public PlayerData getDataHandler(){
		if(players!=null){
			return players;
		}
		return null;
	}
	
	private void error(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
	
	private int getPlayerSetViewDistance(Player player) {
		if(players.playerSetDistance!=null){
			if(players.playerSetDistance.containsKey(player.getUniqueId())){
				if(players.playerSetDistance.get(player.getUniqueId())!=null)
					return players.playerSetDistance.get(player.getUniqueId());
			}	
		}
		return -1;
	}

	private int getPermissionViewDistance(Player player) {
		if(permission.viewDistanceByPermission(player)!=-1)
			return permission.viewDistanceByPermission(player);
		return -1;
	}
	
	private int getDynamicViewDistance(){
		if(this.getDynamic().getGlobalViewDistance()!=-1)
			return this.getDynamic().getGlobalViewDistance();
		return -1;
	}
	
	public int getViewDistance(Player player){
		int distance = this.getServer().getViewDistance();
		if (player.hasPermission(permission.viewDistanceBypass)) {
			player.sendMessage("HasBypass:  " + distance);
            return distance;
        }
		if(this.getConfigAccessor().elytraViewDistance){
			if (config.elytraDistance >= 0 && player.isGliding()){
				player.sendMessage("ElytraSetDistance: " + config.elytraDistance);
				distance = config.elytraDistance;
				return distance;
			}
		}
		if(this.getConfigAccessor().permissionViewDistance){
			if(this.getPermissionViewDistance(player)!=-1 && distance!=this.getPermissionViewDistance(player)){
				if(this.getPermissionViewDistance(player) < distance){
					distance = this.getPermissionViewDistance(player);
	    			player.sendMessage("PermissionViewDistance: " + distance);
				}		
    		}
		}
		if(this.getConfigAccessor().commandViewDistance){
    		if(this.getPlayerSetViewDistance(player)!=-1 && distance!=this.getPlayerSetViewDistance(player)){
    			if(this.getPlayerSetViewDistance(player) < distance){
    				distance = this.getPlayerSetViewDistance(player);
        			player.sendMessage("PlayerSetDistance: " + distance);
    			}   			
    		}
    	}
		if(this.getConfigAccessor().dynamicViewDistance){
    		if(this.getDynamicViewDistance()!=-1 && distance!=this.getDynamicViewDistance()){
    			if(this.getDynamicViewDistance() < distance){
    				distance = this.getDynamicViewDistance();
    			}
    		}  			
    	}
		player.sendMessage("FinalViewDistance: " + distance);
		return distance;
	}
	
	public void setViewDistance(Player player){
		player.setViewDistance(this.getViewDistance(player));
	}

	public DynamicTPSHandler getDynamic() {
		return dynamic;
	}
	
	public PermissionHandler getPermHandler(){
		return permission;
	}
}
