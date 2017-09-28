package com.royalninja.chunkblocks.listeners;

import java.util.List; 

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.royalninja.chunkblocks.Main;
import com.royalninja.chunkblocks.utils.BlockUtil;
import com.royalninja.chunkblocks.utils.HologramUtil;
import com.royalninja.chunkblocks.utils.ItemUtil;
import com.royalninja.chunkblocks.utils.SettingsManager;

public class ChunkBlockPlace implements Listener {

	SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.isCancelled()) return;
		
		Player p = e.getPlayer();
		Block b = e.getBlock();
		ItemStack i = e.getItemInHand();
		
		FileConfiguration config = Main.plugin.getConfig();
		
		String place = config.getString("chunk-block-place-message");
		
		if (!i.getType().equals(ItemUtil.fromId(config.getString("chunkblock-item.id"), 1).getType())) return;
		if (!i.hasItemMeta() && !i.getItemMeta().hasDisplayName()) return;
	
		if (i.getItemMeta().getDisplayName().equals(ItemUtil.convertChatColor(config.getString("chunkblock-item.display-name")))) {
			List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
			for (Integer id : blocks) {
				String generatorWorld = settings.getBlockData().getString(id+".Location.World");
				Double generatorX = settings.getBlockData().getDouble(id+".Location.X");
				Double generatorY = settings.getBlockData().getDouble(id+".Location.Y");
				Double generatorZ = settings.getBlockData().getDouble(id+".Location.Z");
				
				Location generatorLocation = new Location(Bukkit.getWorld(generatorWorld),generatorX,generatorY,generatorZ);
				
				if (b.getLocation().equals(generatorLocation)) {
					HologramUtil.createChunkHologram(id);
				}
			}
			p.sendMessage(ItemUtil.convertChatColor(place));
			BlockUtil.registerChunkBlock(i, b.getLocation(), p);
			return;
			
		}
		
		
		
		
	}
}
