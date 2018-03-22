package com.desle.staffmode;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EntityUtil {

	
	public static Entity getPlayerInSight(Player player) {
		Entity entity = null;
		
		for (Entity nearbyEntity : player.getNearbyEntities(10, 10, 10)) {
			if (nearbyEntity instanceof Player && isLookingAtEntity(player, nearbyEntity))
				entity = nearbyEntity;
		}
		
		return entity;
	}
	
	public static boolean isLookingAtEntity(Player player, Entity entity) {
		Location eye = player.getEyeLocation();
	    Vector toEntity = entity.getLocation().toVector().subtract(eye.toVector());
	    double dot = toEntity.normalize().dot(eye.getDirection());
	    
		if (dot > 0.99D)
			return true;
		return false;
	}
}
 