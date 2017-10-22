package me.drkmatr1984.customviewdistance.listeners;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

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
    public void onPlayerChangedChunk(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();
        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == from.getBlockZ()) {
            return;
        }
        Chunk toChunk = to.getChunk();
        Chunk fromChunk = from.getChunk();
        if (toChunk.getX() == fromChunk.getX() && toChunk.getZ() == fromChunk.getZ()) {
            return;
        }
        plugin.setViewDistance(event.getPlayer());
    }
}