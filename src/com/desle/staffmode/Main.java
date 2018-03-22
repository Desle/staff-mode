package com.desle.staffmode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.desle.staffmode.commands.CommandHandler;
import com.desle.staffmode.events.StaffModeEvents;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		initializeCommands();
		initializeEvents();
	}
	
	@Override
	public void onDisable() {
		for (StaffModePlayer staffModePlayer : StaffModePlayer.map.values())
			if (staffModePlayer.getStatus())
				staffModePlayer.toggle();
	}
	
	public void initializeCommands() {
		getCommand("staffmode").setExecutor(new CommandHandler());
		getCommand("sm").setExecutor(new CommandHandler());
	}
	
	public void initializeEvents() {
		Bukkit.getPluginManager().registerEvents(new StaffModeEvents(), this);
	}

}
