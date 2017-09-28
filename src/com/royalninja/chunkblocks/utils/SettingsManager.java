package com.royalninja.chunkblocks.utils;

import java.io.File; 
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SettingsManager {
	
	  static SettingsManager instance = new SettingsManager();
	  Plugin p;
	  
	  
	  FileConfiguration BlockData;
	  File BlockDataFile;
	  
	  public static SettingsManager getInstance() {
	    return instance;
	  }
	  
	  public void setup(Plugin p) {
	    if (!p.getDataFolder().exists()) {
	      p.getDataFolder().mkdir();
	    }
	    
	    BlockDataFile = new File(p.getDataFolder(), "BlockData.yml");
	    

	    if (!BlockDataFile.exists()) {
	        try {
	          this.BlockDataFile.createNewFile();
	        }
	        catch (IOException e) {
	          Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create BlockData.yml!");
	        }
	    }
	    
	    BlockData = YamlConfiguration.loadConfiguration(this.BlockDataFile);
	    
	  }
	  

	  public FileConfiguration getBlockData() {
		return BlockData;
	  }
	  

	  public void saveBlockData() {
		    try {
		    	BlockData.save(BlockDataFile);
		    }
		    catch (IOException e) {
		      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save BlockData.yml!");
		    }
	  }
}
