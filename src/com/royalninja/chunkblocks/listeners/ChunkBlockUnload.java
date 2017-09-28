package com.royalninja.chunkblocks.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.royalninja.chunkblocks.utils.BlockUtil;
import com.royalninja.chunkblocks.utils.SettingsManager;

public class ChunkBlockUnload implements Listener {

	SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void chunkBlockUnload(ChunkUnloadEvent e) {
		Chunk unload = e.getChunk();
		
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);
		
		for (Integer i : blocks) {
			String blockWorld = settings.getBlockData().getString(i+".Location.World");
			Integer blockX = settings.getBlockData().getInt(i+".Location.X");
			Integer blockY = settings.getBlockData().getInt(i+".Location.Y");
			Integer blockZ = settings.getBlockData().getInt(i+".Location.Z");
			Location blockLocation = new Location(Bukkit.getWorld(blockWorld),blockX,blockY,blockZ);
			Chunk chunkBlock = blockLocation.getChunk();
			
			if (unload.equals(chunkBlock)) {
				e.setCancelled(true);
			}
		}
	}
	
}
