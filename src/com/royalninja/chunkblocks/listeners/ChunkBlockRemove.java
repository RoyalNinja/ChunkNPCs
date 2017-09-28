package com.royalninja.chunkblocks.listeners;

import java.util.ArrayList; 
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.royalninja.chunkblocks.Main;
import com.royalninja.chunkblocks.utils.BlockUtil;
import com.royalninja.chunkblocks.utils.HologramUtil;
import com.royalninja.chunkblocks.utils.ItemUtil;
import com.royalninja.chunkblocks.utils.SettingsManager;

public class ChunkBlockRemove implements Listener {
	
	SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (e.isCancelled()) return;
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);
		
		FileConfiguration config = Main.plugin.getConfig();
		
		List<Integer> toRemove = new ArrayList<Integer>();
		
		for (Integer i : blocks) {
			String blockWorld = settings.getBlockData().getString(i+".Location.World");
			Integer blockX = settings.getBlockData().getInt(i+".Location.X");
			Integer blockY = settings.getBlockData().getInt(i+".Location.Y");
			Integer blockZ = settings.getBlockData().getInt(i+".Location.Z");
			Location blockLocation = new Location(Bukkit.getWorld(blockWorld), blockX, blockY, blockZ);
			
			if (b.getLocation().equals(blockLocation)) {
				
				Boolean ownerBreak = config.getBoolean("only-removable-by-owner");
				Boolean toDrop = config.getBoolean("drop-block-on-destroy");
				String owner = settings.getBlockData().getString(i+".Owner");
				
				if (ownerBreak && !p.getName().equals(owner)) {
					p.sendMessage(ItemUtil.convertChatColor(config.getString("not-owner-place-message")));
					return;
				}
				e.setCancelled(true);
				p.sendMessage(ItemUtil.convertChatColor(config.getString("chunk-block-destroy-message")));
				b.setType(Material.AIR);
				if (toDrop){
					p.getWorld().dropItemNaturally(b.getLocation(), ItemUtil.getChunkBlock());
				}
				for (Integer str : blocks) {
				    if (str == i) {
				       toRemove.add(str);
					}
				}
				HologramUtil.removeChunkHologram(i);
				settings.getBlockData().set(i+"", null);
				settings.saveBlockData();
					
				break;
			}
		}
		blocks.removeAll(toRemove);
		settings.getBlockData().set("TotalBlocks", blocks);
		
		settings.saveBlockData();
	}
}
