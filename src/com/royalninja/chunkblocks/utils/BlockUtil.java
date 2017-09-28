package com.royalninja.chunkblocks.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BlockUtil {
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	public static void loadChunks() {
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);
		
		for (Integer i : blocks) {
			String blockWorld = settings.getBlockData().getString(i+".Location.World");
			Integer blockX = settings.getBlockData().getInt(i+".Location.X");
			Integer blockY = settings.getBlockData().getInt(i+".Location.Y");
			Integer blockZ = settings.getBlockData().getInt(i+".Location.Z");
			Location blockLocation = new Location(Bukkit.getWorld(blockWorld),blockX,blockY,blockZ);
			Chunk chunkBlock = blockLocation.getChunk();
			
			chunkBlock.load();
		}
	}
	
	public static void registerChunkBlock(ItemStack block, Location location, Player owner) {

		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		registerTotalChunkBlocks(blocks);
		
		Integer nextBlock = getHighestInt(blocks)+1;
		
		blocks.add(nextBlock);

		settings.getBlockData().set(nextBlock+".Owner", owner.getName());
		settings.getBlockData().set(nextBlock+".Location.World", location.getWorld().getName());
		settings.getBlockData().set(nextBlock+".Location.X", location.getBlockX());
		settings.getBlockData().set(nextBlock+".Location.Y", location.getBlockY());
		settings.getBlockData().set(nextBlock+".Location.Z", location.getBlockZ());
		
		settings.getBlockData().set("TotalBlocks", blocks);
		
		settings.saveBlockData();
	}

	public static void registerTotalChunkBlocks(List<Integer> blocks) {
		if (blocks == null) {
			List<Integer> blocksList = new ArrayList<Integer>();
			blocksList.add(0);
			settings.getBlockData().set("TotalBlocks", blocksList);
			settings.saveBlockData();
		}
	}
	
	public static int getHighestInt(List<Integer> ints) {
    	int highest = 0;
    	for(Integer i : ints) { 
    		if(highest > i) continue;
    		if(highest == i) continue;
    		if(highest < i) highest = i;
    	}
    	return highest;
    }
}
