package com.desle.staffmode.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.desle.staffmode.StaffModePlayer;

public class CommandHandler implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		if (!label.equalsIgnoreCase("staffmode") && !label.equalsIgnoreCase("sm"))
			return false;
		
		if (!(sender instanceof Player))
			return false;
		
		Player player = (Player) sender;
		
		if (!StaffModePlayer.hasPermission(player))
			return false;
		
		StaffModePlayer.get(player, true).toggle();
		
		return true;
	}

}
