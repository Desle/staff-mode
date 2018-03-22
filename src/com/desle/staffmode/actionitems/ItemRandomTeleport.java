package com.desle.staffmode.actionitems;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;

public class ItemRandomTeleport extends ActionItem {

	public ItemRandomTeleport() {
		super(Material.EYE_OF_ENDER, (byte) 0, ChatColor.GREEN + "Random player", ItemType.RANDOMTELEPORT, ChatColor.GRAY + "Teleport to a", ChatColor.GRAY + "random player.");
	}

	@Override
	public void onUse(Player player) {
		if (Bukkit.getOnlinePlayers().size() == 1) {
			playErrorSound(player);
			return;
		}
		
		Player target = getRandomPlayer(player.getUniqueId());
		player.teleport(target);
		playSuccessSound(player);
	}
	
	
	private Player getRandomPlayer(UUID uuid) {
	    return Bukkit.getOnlinePlayers().stream().filter(p -> !p.getUniqueId().equals(uuid)).findAny().get();
	}

}
