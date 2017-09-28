package com.royalninja.chunkblocks.utils;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.royalninja.chunkblocks.Main;

public class HologramUtil {
	
public static HashMap<Integer, Hologram> chunkHolograms = new HashMap<Integer, Hologram>();
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	public static void createChunkHologram(Integer id) {
		
		String generatorWorld = settings.getBlockData().getString(id+".Location.World");
		Double generatorX = settings.getBlockData().getDouble(id+".Location.X");
		Double generatorY = settings.getBlockData().getDouble(id+".Location.Y");
		Double generatorZ = settings.getBlockData().getDouble(id+".Location.Z");
		
		Location generatorLocation = new Location(Bukkit.getWorld(generatorWorld),generatorX,generatorY,generatorZ);
			
		String holoMessage = Main.plugin.getConfig().getString("hologram-display");
		
		Hologram holo = new Hologram(ItemUtil.convertChatColor(holoMessage));
		holo.show(generatorLocation.add(0.5,1.3,0.5));
		
		chunkHolograms.put(id, holo);
	}
	
	public static void removeChunkHologram(Integer id) {
		
		Hologram hologram = chunkHolograms.get(id);
		
		hologram.destroy();
		
		chunkHolograms.remove(id);
		
	}
	
	public static void updateChunkHologram(Integer id) {
		
		Hologram hologram = chunkHolograms.get(id);

		String holoMessage = Main.plugin.getConfig().getString("hologram-display");
		
		hologram.change(ItemUtil.convertChatColor(holoMessage));
		
		chunkHolograms.put(id, hologram);
	}
	
	public static void cleanUpHolograms() {
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);

		for (Integer i : blocks) {
			
			Hologram hologram = chunkHolograms.get(i);
			
			hologram.destroy();
			
			chunkHolograms.remove(i);
			
		}
	}
	
	public static void setupUpHolograms() {
		
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);

		for (Integer i : blocks) {
			
			String generatorWorld = settings.getBlockData().getString(i+".Location.World");
			Double generatorX = settings.getBlockData().getDouble(i+".Location.X");
			Double generatorY = settings.getBlockData().getDouble(i+".Location.Y");
			Double generatorZ = settings.getBlockData().getDouble(i+".Location.Z");
			
			Location generatorLocation = new Location(Bukkit.getWorld(generatorWorld),generatorX,generatorY,generatorZ);
			
			String holoMessage = Main.plugin.getConfig().getString("hologram-display");
			
			Hologram holo = new Hologram(ItemUtil.convertChatColor(holoMessage));
			holo.show(generatorLocation.add(0.5,1.3,0.5));
			
			chunkHolograms.put(i, holo);
			
		}
		
	}
	
}
