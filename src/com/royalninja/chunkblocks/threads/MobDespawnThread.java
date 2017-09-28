package com.royalninja.chunkblocks.threads;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.royalninja.chunkblocks.Main;
import com.royalninja.chunkblocks.utils.BlockUtil;
import com.royalninja.chunkblocks.utils.SettingsManager;

public class MobDespawnThread extends BukkitRunnable {

	SettingsManager settings = SettingsManager.getInstance();
	
	public void run() {
		
		List<Integer> blocks = settings.getBlockData().getIntegerList("TotalBlocks");
		BlockUtil.registerTotalChunkBlocks(blocks);
		
		for (Integer i : blocks) {
			String blockWorld = settings.getBlockData().getString(i+".Location.World");
			Integer blockX = settings.getBlockData().getInt(i+".Location.X");
			Integer blockY = settings.getBlockData().getInt(i+".Location.Y");
			Integer blockZ = settings.getBlockData().getInt(i+".Location.Z");
			Location blockLocation = new Location(Bukkit.getWorld(blockWorld),blockX,blockY,blockZ);
			
			Integer radius = Main.plugin.getConfig().getInt("chunk-block-mob-check-radius");
			
			for (Entity near : blockLocation.getWorld().getNearbyEntities(blockLocation, radius,radius,radius)) {
				if (near.isDead()) return;
				if(near instanceof LivingEntity) {
					LivingEntity living = (LivingEntity) near;
					living.setRemoveWhenFarAway(false);
				}
			}
			
		}
		
	}

}
