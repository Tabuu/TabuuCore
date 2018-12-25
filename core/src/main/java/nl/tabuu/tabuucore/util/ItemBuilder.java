/** Copyright 2018, Rick van Sloten
	All rights reserved.
*/

package nl.tabuu.tabuucore.util;

import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is an easy item builder.
 *
 * @author Rick van Sloten (Tabuu)
 */
public class ItemBuilder {

	private ItemStack _itemStack;
	private INBTTagCompound _tagCompound;

	/**
	 * Creates the ItemBuilder based on a clone of an already existing itemstack.
	 * @param itemStack
	 */
	public ItemBuilder(ItemStack itemStack) {
		_itemStack = itemStack.clone();
		_tagCompound = INBTTagCompound.get(itemStack);
	}

	/**
	 * Creates the ItemBuilder based on a material (item count will be set to 1).
	 * @param material
	 */
	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	/**
	 * Builds the item based on the item.
	 * @return The item stack.
	 */
	public ItemStack build() {
		_tagCompound.apply(_itemStack);
		return _itemStack;
	}

	/**
	 * Sets the item count of the item stack
	 * @param material New item type
	 * @return The item.
	 */
	public ItemBuilder setMaterial(Material material){
		_itemStack.setType(material);
		return this;
	}

	/**
	 * Sets the item count of the item stack
	 * @param amount New item count
	 * @return The item.
	 */
	public ItemBuilder setAmount(int amount) {
		_itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Sets the display name of the item.
	 * @param name New display name.
	 * @return The item.
	 */
	public ItemBuilder setDisplayName(String name) {
		ItemMeta meta = _itemStack.getItemMeta();
		
		meta.setDisplayName(name);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}


	/**
	 * Sets the localized name of the item (ONLY WORKS FOR 1.12+).
	 * @param name New localized name.
	 * @return The item.
	 */
	public ItemBuilder setLocalizedName(String name) {
		ItemMeta meta = _itemStack.getItemMeta();

		meta.setLocalizedName(name);

		_itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Adds lore to existing lore of the item.
	 * @param lore String object containing the lore line(s) that will be added.
	 * @return The item.
	 */
	public ItemBuilder addLore(String... lore) {
		ItemMeta meta = _itemStack.getItemMeta();
		
		List<String> oldLore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
		oldLore.addAll(Arrays.asList(lore));
		
		meta.setLore(oldLore);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Sets the lore of the item.
	 * @param lore String object containing the new lore line(s).
	 * @return The item.
	 */
	public ItemBuilder setLore(String... lore) {
		ItemMeta meta = _itemStack.getItemMeta();
		
		meta.setLore(Arrays.asList(lore));
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	public List<String> getLore(){
		ItemMeta meta = _itemStack.getItemMeta();

		if(meta.hasLore())
			return meta.getLore();
		else
			return new ArrayList<String>();
	}

	/**
	 * Removes all the lore of the item.
	 * @return The item.
	 */
	public ItemBuilder clearLore() {
		ItemMeta meta = _itemStack.getItemMeta();
		
		meta.setLore(null);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Adds item flags to the item.
	 * @param flags object containing the item flags to be added.
	 * @return The item.
	 */
	public ItemBuilder addItemFlags(ItemFlag... flags) {
		ItemMeta meta = _itemStack.getItemMeta();

		meta.addItemFlags(flags);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Removes item flags of the item.
	 * @param flags object containing the item flags to be removed.
	 * @return The item.
	 */
	public ItemBuilder removeFlags(ItemFlag... flags) {
		ItemMeta meta = _itemStack.getItemMeta();
		
		meta.removeItemFlags(flags);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Adds an (unsafe) enchantment to the item.
	 * @param ench Enchantment type
	 * @param level Level of the enchantment
	 * @return The item.
	 */
	public ItemBuilder addEnchantment(Enchantment ench, int level) {
		_itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	/**
	 * Removes an (unsafe) enchantment from the item.
	 * @param ench Enchantment type
	 * @return The item.
	 */
	public ItemBuilder removeEnchantment(Enchantment ench) {
		_itemStack.removeEnchantment(ench);
		return this;
	}

	/**
	 * Sets the durability of the item.
	 * @param durability New durability.
	 * @return The item.
	 */
	public ItemBuilder setDurability(short durability) {
		_itemStack.setDurability(durability);
		return this;
	}

	public INBTTagCompound getNBTTagCompound(){
		return _tagCompound;
	}
}
