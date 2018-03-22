package com.desle.staffmode;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.desle.staffmode.ActionItem.ItemType;

public class StaffModePlayer {
	
	public static Map<UUID, StaffModePlayer> map = new HashMap<UUID, StaffModePlayer>();
	
	private ItemStack[] cachedItems;
	private ItemStack[] cachedArmor;
	private boolean cachedFlying;
	
	private UUID uuid;
	boolean status = false;
	
	public StaffModePlayer(Player player) {
		this.uuid = player.getUniqueId();
		
		map.put(this.uuid, this);
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public static StaffModePlayer get(Player player, boolean force) {
		UUID uuid = player.getUniqueId();
		
		if (map.containsKey(uuid))
			return map.get(uuid);
		
		if (force)
			return new StaffModePlayer(player);
		
		return null;
	}
	
	public boolean getStatus() {
		return this.status;
	}
	
	
	public static boolean hasPermission(Player player) {
		if (player.isOp())
			return true;
		
		if (player.hasPermission("staffmode.*"))
			return true;
		
		return false;
	}
	
	public void toggle() {
		boolean status = this.status;
		
		if (status) {
			//disabling
			unload();
		} else {
			//enabling
			load();
		}
		
		this.status = !status;
	}
	
	//when toggling mode on
	public boolean load() {
		Player player = Bukkit.getPlayer(this.uuid);
		
		if (player == null || !player.isOnline())
			return false;
		
		this.cachedItems = player.getInventory().getContents();
		this.cachedArmor = player.getInventory().getArmorContents();
		this.cachedFlying = player.getAllowFlight();
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setAllowFlight(true);
		player.performCommand("/v");
		
		for (ItemType itemType : ItemType.getAllFor(this)) {
			ActionItem actionItem = itemType.getActionItem();
			player.getInventory().addItem(actionItem.getItemStack());
		}
		
		return true;
	}

	//when toggling mode off
	public boolean unload() {
		Player player = Bukkit.getPlayer(this.uuid);
		
		if (player == null || !player.isOnline())
			return false;
		
		player.getInventory().setArmorContents(this.cachedArmor);
		player.getInventory().setContents(this.cachedItems);
		
		player.setAllowFlight(this.cachedFlying);
		
		return true;
	}

}
