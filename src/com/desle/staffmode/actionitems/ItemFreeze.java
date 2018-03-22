package com.desle.staffmode.actionitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;
import com.desle.staffmode.EntityUtil;

public class ItemFreeze extends ActionItem {

	public ItemFreeze() {
		super(Material.ANVIL, (byte) 0, ChatColor.RED + "Freeze player", ItemType.FREEZE, ChatColor.GRAY + "Select a player to", ChatColor.GRAY + "lock in position.");
	}

	@Override
	public void onUse(Player player) {
		Entity targetEntity = EntityUtil.getPlayerInSight(player);
		
		if (targetEntity == null) {
			playErrorSound(player);
			return;
		}
		
		Player target = (Player) targetEntity;
		player.performCommand("/freeze " + target.getName());
		
		playSuccessSound(player);
	}

}
