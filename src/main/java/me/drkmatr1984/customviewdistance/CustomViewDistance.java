package me.drkmatr1984.customviewdistance;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.drkmatr1984.customviewdistance.commands.CommandHandler;
import me.drkmatr1984.customviewdistance.configs.ConfigAccessor;
import me.drkmatr1984.customviewdistance.configs.PlayerData;
import me.drkmatr1984.customviewdistance.handlers.DynamicTPSHandler;
import me.drkmatr1984.customviewdistance.handlers.PermissionHandler;
import me.drkmatr1984.customviewdistance.listeners.EventListener;
import me.drkmatr1984.customviewdistance.tasks.dynamicSetTPSTask;

public class CustomViewDistance extends JavaPlugin implements Listener {
	
	private ConfigAccessor config;
	private PlayerData players;
	private DynamicTPSHandler dynamic;
	private PermissionHandler permission;
	
    @Override
    public void onEnable() {
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
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        config = new ConfigAccessor(this);
        if(this.getConfigAccessor().getMaxViewDistance() > this.getServer().getViewDistance())
			this.getConfigAccessor().setMaxViewDistance(this.getServer().getViewDistance());
        this.dynamic.setGlobalViewDistance(this.getConfigAccessor().getMaxViewDistance());
        if(config.permissionViewDistance){
        	permission = new PermissionHandler(this);
        }      
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);       
        if(config.commandViewDistance){
        	players = new PlayerData(this);
        	getCommand("viewdistance").setExecutor(new CommandHandler(this));
        }        
        if(config.dynamicViewDistance){
        	try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        	dynamic = new DynamicTPSHandler(this);
        	dynamic.calculateViewDistance();
        	new dynamicSetTPSTask(this).runTaskTimer(this, 20, config.dynamicSetInterval*20);
        }        
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
		if(players.playerSetDistance.get(player.getUniqueId())!=null)
			return players.playerSetDistance.get(player.getUniqueId());
		return 0;
	}

	private int getPermissionViewDistance(Player player) {
		if(permission.viewDistanceByPermission(player)!=0)
			return permission.viewDistanceByPermission(player);
		return 0;
	}
	
	private int getDynamicViewDistance(){
		if(dynamic.getGlobalViewDistance()!=0)
			return dynamic.getGlobalViewDistance();
		return 0;
	}
	
	public int getViewDistance(Player player){
		int distance = this.dynamic.getGlobalViewDistance();
		if (player.isOp()) {
            return distance;
        }
		if(config.elytraViewDistance){
			if (config.elytraDistance >= 0 && player.isGliding())
				return config.elytraDistance;
		}
		if(this.getConfigAccessor().commandViewDistance){
    		if(this.getPlayerSetViewDistance(player)!=0 && distance!=this.getPlayerSetViewDistance(player)){
    			distance = this.getPlayerSetViewDistance(player);
    			player.sendMessage("PlayerSetDistance: " + distance);
    		}
    	}
		if(this.getConfigAccessor().permissionViewDistance){
			if(this.getPermissionViewDistance(player)!=0 && distance!=this.getPermissionViewDistance(player)){
    			distance = this.getPermissionViewDistance(player);
    			player.sendMessage("PermissionViewDistance: " + distance);
    		}
		}
		if(this.getConfigAccessor().dynamicViewDistance){
    		if(this.getDynamicViewDistance()!=0 && distance!=this.getDynamicViewDistance()){
    			if(this.getDynamicViewDistance() < distance)
    				distance = this.getDynamicViewDistance();
        		player.sendMessage("DynamicViewDistance: " + distance);  			
    		}  			
    	}
		player.sendMessage("FinalViewDistance: " + distance);
		return distance;
	}
	
	public void setViewDistance(Player player){
		int distance = getViewDistance(player);
		player.setViewDistance(distance);
	}
}
