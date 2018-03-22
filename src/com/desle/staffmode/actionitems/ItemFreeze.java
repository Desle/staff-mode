package com.desle.staffmode.actionitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;

public class ItemFreeze extends ActionItem {

	public ItemFreeze() {
		super(Material.ANVIL, (byte) 0, ChatColor.RED + "Freeze player", ItemType.FREEZE, ChatColor.GRAY + "Select a player to", ChatColor.GRAY + "lock in position.");
	}

	@Override
	public void onUse(Player player) {
	}

	@Override
	public void onUse(Player player, Entity target) {		
		if (!(target instanceof Player)) {
			playErrorSound(player);
			return;
		}
		
		Player targetPlayer = (Player) target;
		player.performCommand("/freeze " + targetPlayer.getName());
		
		playSuccessSound(player);
	}

}
