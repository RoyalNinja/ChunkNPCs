package com.royalninja.chunkblocks;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.royalninja.chunkblocks.listeners.ChunkBlockPlace;
import com.royalninja.chunkblocks.listeners.ChunkBlockRemove;
import com.royalninja.chunkblocks.listeners.ChunkBlockUnload;
import com.royalninja.chunkblocks.threads.MobDespawnThread;
import com.royalninja.chunkblocks.utils.BlockUtil;
import com.royalninja.chunkblocks.utils.HologramUtil;
import com.royalninja.chunkblocks.utils.ItemUtil;
import com.royalninja.chunkblocks.utils.SettingsManager;

public class Main extends JavaPlugin {
	
	public static int MobDespawnTimer;
	
	public static Plugin plugin;
	SettingsManager settings = SettingsManager.getInstance();
	
	public void onEnable() {
		plugin = this;
		settings.setup(this);
		setupConfig();
		setupListeners();
		setupThreads();
		BlockUtil.loadChunks();
		HologramUtil.setupUpHolograms();
	}
	public void onDisable() {
		HologramUtil.cleanUpHolograms();
	}
	@SuppressWarnings("deprecation")
	void setupThreads() {
		Integer mobDespawnRate = getConfig().getInt("chunk-block-mob-check-rate");
		Boolean disableMobDespawning = getConfig().getBoolean("disable-mob-despawning");
		if (disableMobDespawning) {
			MobDespawnTimer = getServer().getScheduler().scheduleAsyncRepeatingTask(this,
					new MobDespawnThread(), mobDespawnRate, mobDespawnRate);	
		}
	}
	void setupListeners() {
		Bukkit.getServer().getPluginManager().registerEvents(new ChunkBlockPlace(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChunkBlockRemove(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ChunkBlockUnload(), this);
	}
	void setupConfig() {
		saveDefaultConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chunkblock")) {
			if (!sender.hasPermission(getConfig().getString("no-permission-execute-command-message"))) {
				sender.sendMessage(ItemUtil.convertChatColor(getConfig().getString("no-permission-execute-command-message")));
				return true;
			}
			if (args.length != 1) {
				sender.sendMessage(ItemUtil.convertChatColor(getConfig().getString("invalid-command-message")));
				return true;
			}
			if (Bukkit.getServer().getPlayer(args[0])==null) {
				sender.sendMessage(ItemUtil.convertChatColor(getConfig().getString("command-player-not-found-message").replaceAll("%player%", args[0])));
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			String give = ItemUtil.convertChatColor(getConfig().getString("chunk-block-give-message").replaceAll("%target%", target.getName()));
			String receive = ItemUtil.convertChatColor(getConfig().getString("chunk-block-receive-message").replaceAll("%player%", sender.getName()));
			target.getInventory().addItem(ItemUtil.getChunkBlock());
			target.sendMessage(receive);
			sender.sendMessage(give);
			return true;
		}
		return true;
	}	
}
