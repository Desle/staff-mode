package com.desle.staffmode.actionitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;

public class ItemVanish extends ActionItem {

	public ItemVanish() {
		super(Material.LEVER, (byte) 0, ChatColor.AQUA + "Vanish", ItemType.VANISH, ChatColor.GRAY + "You're currently", ChatColor.GRAY + "visible to others.");
	}

	@Override
	public void onUse(Player player) {
		player.performCommand("/v");
		
		int slot = player.getInventory().first(getItemStack());		
		player.getInventory().setItem(slot, new ItemUnvanish().getItemStack());
		
		playSuccessSound(player);
	}

}