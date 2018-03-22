package com.desle.staffmode.actionitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.desle.staffmode.ActionItem;

public class ItemUnvanish extends ActionItem {

	public ItemUnvanish() {
		super(Material.REDSTONE_TORCH_ON, (byte) 0, ChatColor.AQUA + "Unvanish", ItemType.UNVANISH, ChatColor.GRAY + "You're currently", ChatColor.GRAY + "invisible to others.");
	}

	@Override
	public void onUse(Player player) {
		player.performCommand("/v");
		
		int slot = player.getInventory().first(getItemStack());
		player.getInventory().setItem(slot, new ItemVanish().getItemStack());
		
		playSuccessSound(player);
	}

	@Override
	public void onUse(Player player, Entity target) {
	}
}
