/*
 * Copyright (c) 2014, 2018, Rick van Sloten. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */

package nl.tabuu.tabuucore.util;

import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.NMSVersion;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.vector.Vector3f;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.*;

/** Useful functions related to Bukkit/Spigot
 * @author Rick van Sloten (Tabuu)
 */
public class BukkitUtils {
	/**
	 * @param	location center block.
	 * @param	radius radius of the blocks.
	 * @return	a list of all blocks in that radius.
	 */
	public static List<Block> getBlocksInRadius(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        
        for(int x = -radius; x <= radius; x++)
        	for(int z = -radius; z <= radius; z++)
        		for(int y = -radius; y <= radius; y++)
        			blocks.add(new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z).getBlock());
        
        return blocks;
    }

	/**
	 * @param	location center block.
	 * @param	radius radius of the blocks.
	 * @return	a list of all blocks in that radius.
	 */
	public static List<Block> getBlocksInRadius(Location location, Vector3f radius) {
		List<Block> blocks = new ArrayList<>();

		for(int x = -radius.getIntX(); x <= radius.getIntX(); x++)
			for(int z = -radius.getIntZ(); z <= radius.getIntZ(); z++)
				for(int y = -radius.getIntY(); y <= radius.getIntY(); y++)
					blocks.add(new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z).getBlock());

		return blocks;
	}

	/**
	 * @param	location location of the center block.
	 * @param	x radius of the blocks on the x axis.
	 * @param	y radius of the blocks on the y axis.
	 * @param	z radius of the blocks on the z axis.
	 * @return	a list of all blocks in that radius.
	 */
	public static List<Block> getBlocksInRadius(Location location, int x, int y, int z) {
		return getBlocksInRadius(location, new Vector3f(x, y, z));
	}

	/**
	 * This method can be used to get the {@link ItemStack} in a players hand or main hand depending on the Bukkit version.
	 * @param player target player.
	 * @return the {@link ItemStack} a player is holding in his main hand.
	 */
	@SuppressWarnings("deprecation")
	public static ItemStack getItemInMainHand(Player player){
		if(NMSUtil.getVersion().isPreOrEquals(NMSVersion.v1_8_R3))
			return player.getInventory().getItemInHand();
		else
			return player.getInventory().getItemInMainHand();
	}

	/**
	 * Returns the storage contents of a players inventory.
	 * @param player The player to get the storage contents from.
	 * @return The storage contents of a players inventory.
	 */
	public static ItemStack[] getStorageContents(Player player) {
		if(NMSUtil.getVersion().isPreOrEquals(NMSVersion.v1_8_R3))
			return player.getInventory().getContents();
		else
			return player.getInventory().getStorageContents();
	}

	public static void setStorageContents(Player player, ItemStack[] items) {
		if(NMSUtil.getVersion().isPreOrEquals(NMSVersion.v1_8_R3))
			player.getInventory().setContents(items);
		else
			player.getInventory().setStorageContents(items);
	}

	/**
	 * @param	playerName name of the player.
	 * @return	a {@link OfflinePlayer} with that name or null if no player with that name has ever joined the server.
	 */
	public static OfflinePlayer getOfflinePlayerByName(String playerName){
		return Serializer.OFFLINE_PLAYER.deserialize(playerName);
	}

	/**
	 * Returns true if the material is air or null.
	 * @param material The material to check.
	 * @return True if the material is air or null.
	 */
	public static boolean isAir(Material material) {
		if(Objects.isNull(material)) return true;

		else if(NMSUtil.getVersion().isPostOrEquals(NMSVersion.v1_14_R1))
			return material.isAir();
		else
			return material.equals(Material.AIR);
	}

	/**
	 * Returns true if the item is air or null.
	 * @param item The item to check.
	 * @return True if the item is air or null.
	 */
	public static boolean isAir(ItemStack item) {
		return Objects.isNull(item) || isAir(item.getType());
	}

	/**
	 * Returns true if the material is air or null.
	 * @param material The material to check.
	 * @return True if the material is air or null.
	 */
	public static boolean isAir(XMaterial material) {
		return Objects.isNull(material) || isAir(material.parseItem());
	}

	/**
	 * Returns the inventory that was clicked in the event.
	 * @param event The event to get the clicked inventory from.
	 * @return The inventory that was clicked in the event.
	 */
	public static Inventory getClickedInventory(InventoryClickEvent event) {
		if(NMSUtil.getVersion().isPreOrEquals(NMSVersion.v1_8_R3)) {
			int slot = event.getRawSlot();
			InventoryView view = event.getView();
			Inventory top = view.getTopInventory();
			Inventory bottom = view.getBottomInventory();

			if(slot < 0 || Objects.isNull(top)) return null;
			return slot < top.getSize() ? top : bottom;
		}

		return event.getClickedInventory();
	}

	/**
	 * @param	player Target player.
	 * @param 	range The maximum range the target block has to be in.
	 * @param 	ignored Block types that will be ignored/transparent.
	 * @return	the block a player is looking at.
	 */
	public static Block getTargetBlock(Player player, int range, Material... ignored){
		Set<Material> transparent = new HashSet<>();

		transparent.add(Material.AIR);
		transparent.addAll(Arrays.asList(ignored));

		return player.getTargetBlock(transparent, range);
	}

	/**
	 * @param	player target player.
	 * @param 	range the maximum range the target block has to be in.
	 * @return	the block a player is looking at.
	 */
	public static Block getTargetBlock(Player player, int range){
		Set<Material> transparent = new HashSet<>();
		transparent.add(Material.AIR);

		return player.getTargetBlock(transparent, range);
	}

	/**
	 * Registers a {@link BukkitCommand} with reflection (alternative for using the plugin.yml)
	 * @param	commandLabel Command label.
	 * @param	command {@link BukkitCommand} to be registered.
	 * @return	false if registration failed and true is registration succeeded.
	 */
	public static boolean registerCommand(String commandLabel, BukkitCommand command){
		try {
			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			command.unregister(commandMap);
			commandMap.register(commandLabel, command);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return false;
		}
		return true;
	}
}