package me.drkmatr1984.customviewdistance.configs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.drkmatr1984.customviewdistance.CustomViewDistance;

public class PlayerData{
	
	private File usersFile;
	private File dataFolder;
	private FileConfiguration users;
	
	public HashMap<UUID, Integer> playerSetDistance;
	
	private CustomViewDistance plugin;
	
	public PlayerData(CustomViewDistance plugin){		
		this.plugin = plugin;
		dataFolder = new File(this.plugin.getDataFolder().toString()+"/data");
	}
		
	public void initLists(){
		saveDefaultUserList();
		loadUserList();
	}
	
    ////////////////////////////////////////////////////////////
	public void saveDefaultUserList() {
		//pickup toggle users
		if(!(dataFolder.exists())){
			dataFolder.mkdir();
		}
	    if (usersFile == null) {
	        usersFile = new File(dataFolder, "data.yml");
	    }
	    if (!usersFile.exists()) {           
	        plugin.saveResource("data/data.yml", false);
	    }
	    
    }
	  
	public void loadUserList(){
		//Load Players and their ViewDistances
		users = YamlConfiguration.loadConfiguration(usersFile);
		for(String s : users.getKeys(true)){
			Bukkit.getLogger().info(s);
		}
	}
	  
	public void saveUserList(){
		//Save Players and their ViewDistances
		if(playerSetDistance!=null)
		{
			for(UUID uid : playerSetDistance.keySet()){
				users.set("ViewPlayers." + uid.toString(), playerSetDistance.get(uid));
			}
		}
		if(usersFile.exists())
			usersFile.delete();
		try {
			users.save(usersFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			usersFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
