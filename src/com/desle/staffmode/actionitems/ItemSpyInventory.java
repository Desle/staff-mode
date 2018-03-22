package com.desle.staffmode.actionitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;
import com.desle.staffmode.EntityUtil;

public class ItemSpyInventory extends ActionItem {

	public ItemSpyInventory() {
		super(Material.INK_SACK, (byte) 2, ChatColor.DARK_AQUA + "Spy inventory", ItemType.SPYINVENTORY, ChatColor.GRAY + "View and manage a", ChatColor.GRAY + "player's inventory.");
	}

	@Override
	public void onUse(Player player) {
		Entity targetEntity = EntityUtil.getPlayerInSight(player);
		
		if (targetEntity == null) {
			playErrorSound(player);
			return;
		}
		
		Player target = (Player) targetEntity;
		player.openInventory(target.getInventory());
		playSuccessSound(player);
	}

}
