package com.royalninja.chunkblocks.utils;

import java.util.ArrayList; 
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.royalninja.chunkblocks.Main;

public class ItemUtil {
	
	public static ItemStack getChunkBlock() {
		FileConfiguration config = Main.plugin.getConfig();
		ItemStack chunkBlock = fromId(config.getString("chunkblock-item.id"), 1);
		ItemMeta chunkMeta = chunkBlock.getItemMeta();
		chunkMeta.setDisplayName(convertChatColor(config.getString("chunkblock-item.display-name")));
		List<String> chunkLore = new ArrayList<String>();
		for (String configLine : config.getStringList("chunkblock-item.lore")) {
			chunkLore.add(convertChatColor(configLine));
		}
		chunkMeta.setLore(chunkLore);
		chunkBlock.setItemMeta(chunkMeta);
		return chunkBlock;
	}
	
	
	public static String convertChatColor(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack fromId(String id, int amount) {
		if (id.contains(":")) {
		    String[] s = id.split(":");
		    int type = Integer.parseInt(s[0]); 
		    int data = Integer.parseInt(s[1]); 
		 
		    ItemStack item = new ItemStack(Material.getMaterial(type));
		    item.setDurability((short) data);
		 
		    return item;	
		}else {
			ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(id)));
			return item;
		}
	}
	
}
