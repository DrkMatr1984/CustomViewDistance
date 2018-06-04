package me.drkmatr1984.customviewdistance.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import me.drkmatr1984.customevents.moveEvents.PlayerMovedChunkEvent;
import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class EventListener implements Listener
{
	private CustomViewDistance plugin;
	
	public EventListener(CustomViewDistance plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event) {
		plugin.setViewDistance(event.getPlayer());				
    }
	
	@EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		plugin.setViewDistance(event.getPlayer());
    }

    @EventHandler
    public void onPlayerChangedChunk(PlayerMovedChunkEvent event) {
        plugin.setViewDistance(event.getPlayer());
    }
}