package com.desle.staffmode.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.desle.staffmode.ActionItem;
import com.desle.staffmode.ActionItem.ItemType;
import com.desle.staffmode.StaffModePlayer;

public class StaffModeEvents implements Listener {

	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		ItemStack item = e.getItemDrop().getItemStack();
		ItemType itemType = ItemType.get(item);
		
		if (itemType == null)
			return;
		
		ActionItem actionItem = itemType.getActionItem();
		
		if (actionItem != null)
			e.setCancelled(true);
	}
	
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		StaffModePlayer staffModePlayer = StaffModePlayer.get(player, false);
		
		if (staffModePlayer == null)
			return;
		
		if (!staffModePlayer.getStatus())
			return;
		
		staffModePlayer.toggle();
	}
	
	
	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		StaffModePlayer staffModePlayer = StaffModePlayer.get(player, false);
		if (staffModePlayer == null)
			return;
		
		if (staffModePlayer.getStatus())
			e.setResult(Result.DENY);
		
		player.updateInventory();
	}
	
	
	
	@EventHandler
	public void onUse(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		StaffModePlayer staffModePlayer = StaffModePlayer.get(player, false);
		
		if (staffModePlayer == null)
			return;
		
		if (!staffModePlayer.getStatus())
			return;
		
		ItemStack item = e.getItem();
		
		if (item == null)
			return;
		
		if (ItemType.get(item) == null)
			return;
		
		e.setCancelled(true);
		
		ActionItem actionItem = ItemType.get(item).getActionItem();
		actionItem.onUse(player);
		player.updateInventory();
	}
	
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() == null)
			return;
		
		StaffModePlayer staffModePlayer = StaffModePlayer.get(player, false);
		if (staffModePlayer == null)
			return;
		
		if (!staffModePlayer.getStatus())
			return;
		
		ItemType itemType = ItemType.get(e.getCurrentItem());
		
		if (itemType == null)
			return;
		
		ActionItem actionItem = itemType.getActionItem();
		actionItem.onUse(player);
		
		e.setResult(Result.DENY);
		player.updateInventory();
	}
	
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if (e.getEntityType() != EntityType.PLAYER)
			return;
		
		if (!(e.getDamager() instanceof Player))
			return;
		
		Player player = (Player) e.getDamager();
		
		if (StaffModePlayer.get(player, false) == null)
			return;
		
		StaffModePlayer staffModePlayer = StaffModePlayer.get(player, false);
		
		if (!staffModePlayer.getStatus())
			return;
		
		ItemStack item = player.getItemInHand();
		
		if (ItemType.get(item) == null)
			return;
		
		ActionItem actionItem = ItemType.get(item).getActionItem();
		
		if (actionItem == null)
			return;
		
		e.setCancelled(true);
	}
}