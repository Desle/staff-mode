package com.desle.staffmode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.desle.staffmode.actionitems.ItemFreeze;
import com.desle.staffmode.actionitems.ItemRandomTeleport;
import com.desle.staffmode.actionitems.ItemSpyInventory;
import com.desle.staffmode.actionitems.ItemUnvanish;
import com.desle.staffmode.actionitems.ItemVanish;
import com.desle.staffmode.gui.ItemDataUtil;

import net.minecraft.server.v1_8_R1.NBTTagCompound;

public abstract class ActionItem {

	protected Material material;
	protected String name;
	protected String[] lore;
	protected ItemStack itemStack;
	protected byte data;
	protected ItemType type;
	
	public ActionItem(Material material, byte data, String name, ItemType type, String... lore) {
		this.material = material;
		this.data = (byte) data;
		this.name = name;
		this.lore = lore;
		this.type = type;
	}
	
	public ItemStack getItemStack() {
		if (itemStack != null)
			return itemStack;
		
		return constructItemStack();
	}
	
	public abstract void onUse(Player player);
	public abstract void onUse(Player player, Entity target);
	
	public void playSuccessSound(Player player) {
		player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1, 1);
	}
	
	public void playErrorSound(Player player) {
		player.playSound(player.getLocation(), Sound.LAVA_POP, 1, 1);
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack constructItemStack() {
		this.itemStack = new ItemStack(this.material,1, (short) 0, this.data);
		ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(this.material);
		itemMeta.setDisplayName(this.name);
		itemMeta.setLore(Arrays.asList(this.lore));
		this.itemStack.setItemMeta(itemMeta);
		
		NBTTagCompound compound = ItemDataUtil.getData(this.itemStack);
		compound.setString("staffmode_item", this.type.name());
		this.itemStack = ItemDataUtil.setData(this.itemStack, compound);
		
		return this.itemStack;
	}
	
	public enum ItemType {
		VANISH(ItemVanish.class),
		UNVANISH(ItemUnvanish.class),
		RANDOMTELEPORT(ItemRandomTeleport.class),
		SPYINVENTORY(ItemSpyInventory.class),
		FREEZE(ItemFreeze.class);
		

		private final Class<? extends ActionItem> itemClass;
		
		public ActionItem getActionItem() {
			try {
				return this.itemClass.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				Bukkit.getLogger().warning("Failed to create " + name() + " as ActionItem.");
				return null;
			}
		}
		
		public static List<ItemType> getAllFor(StaffModePlayer staffModePlayer) {
			List<ItemType> itemTypes = new ArrayList<ItemType>();
			
			for (ItemType itemType : values()) {
				boolean status = true;
				
				switch(itemType) {
				case VANISH:
					status = false;
					break;
				default:
					break;
				}
				
				if (status)
					itemTypes.add(itemType);
			}
			
			return itemTypes;
		}
		
		public static ItemType get(ItemStack itemStack) {
			NBTTagCompound compound = ItemDataUtil.getData(itemStack);
			
			return valueOf(compound.getString("staffmode_item"));
		}
		
		private ItemType(Class<? extends ActionItem> itemClass) {
			this.itemClass = itemClass;
		}
	}
}
