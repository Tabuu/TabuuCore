package nl.tabuu.tabuucore.item;

import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Represents an object to build an {@link ItemStack} with.
 */
public class ItemBuilder {

	private ItemStack _itemStack;
	private INBTTagCompound _tagCompound;

	/**
	 * Creates a new ItemBuilder and copies the data from the given {@link ItemStack}.
	 * @param itemStack the {@link ItemStack} to copy the data from.
	 */
	public ItemBuilder(ItemStack itemStack) {
		_itemStack = itemStack.clone();
	}

	/**
	 * Creates the ItemBuilder based on a {@link Material}, the item amount will be set to 1.
	 * @param material the {@link Material}
	 */
	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	/**
	 * Creates the ItemBuilder based on a {@link XMaterial}, the item amount will be set to 1.
	 * @param material the {@link XMaterial}
	 */
	public ItemBuilder(XMaterial material) {
		ItemStack item = material.parseItem();
		assert item != null : "Material cannot result in null item.";
		_itemStack = item.clone();
	}

	/**
	 * Builds the {@link ItemStack} and applies the {@link INBTTagCompound} if needed.
	 * @return the build item.
	 */
	public ItemStack build() {
		if(_tagCompound != null)
			_tagCompound.apply(_itemStack);
		return _itemStack.clone();
	}

	/**
	 * Sets the item type to the given {@link Material}.
	 * @param material new item type.
	 * @return itself.
	 * @see ItemStack#setType(Material)
	 */
	public ItemBuilder setMaterial(Material material){
		_itemStack.setType(material);
		return this;
	}

	/**
	 * Sets the item count to the given amount.
	 * @param amount new item count.
	 * @return itself.
	 * @see ItemStack#setAmount(int)
	 */
	public ItemBuilder setAmount(int amount) {
		_itemStack.setAmount(amount);
		return this;
	}

	/**
	 * Sets the display name to the given name.
	 * @param name new display name.
	 * @return itself.
	 * @see ItemMeta#setDisplayName(String)
	 */
	public ItemBuilder setDisplayName(String name) {
		ItemMeta meta = getItemMeta();

		meta.setDisplayName(name);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}


	/**
	 * Sets the localized name of the item, THIS METHOD ONLY WORKS FOR 1.12+.
	 * @param name new localized name.
	 * @return itself.
	 * @see ItemMeta#setLocalizedName(String)
	 */
	public ItemBuilder setLocalizedName(String name) {
		ItemMeta meta = getItemMeta();

		meta.setLocalizedName(name);

		_itemStack.setItemMeta(meta);

		return this;
	}

	/**
	 * Adds lore to existing lore.
	 * @param lore lore lines to be added to the existing lore.
	 * @return itself.
	 * @see ItemMeta#setLore(List)
	 */
	public ItemBuilder addLore(String... lore) {
		ItemMeta meta = getItemMeta();
		
		List<String> oldLore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
		oldLore.addAll(Arrays.asList(lore));

		setLore(oldLore.toArray(new String[0]));
		
		return this;
	}

	/**
	 * Sets the lore.
	 * @param lore new lore lines to be set.
	 * @return itself.
	 * @see ItemMeta#setLore(List)
	 */
	public ItemBuilder setLore(String... lore) {
		ItemMeta meta = getItemMeta();

		ArrayList<String> newLore = new ArrayList<>();
		for(String string : lore){
			if(string.contains("\n"))
				newLore.addAll(Arrays.asList(string.split("\n")));
			else
				newLore.add(string);
		}

		meta.setLore(newLore);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Returns the lore of the ItemBuilder.
	 * @return the lore of the ItemBuilder.
	 * @see ItemMeta#getLore()
	 */
	public List<String> getLore(){
		ItemMeta meta = getItemMeta();

		if(meta.hasLore())
			return meta.getLore();
		else
			return Collections.emptyList();
	}

	/**
	 * Removes all the lore.
	 * @return itself.
	 * @see ItemMeta#setLore(List)
	 */
	public ItemBuilder clearLore() {
		ItemMeta meta = getItemMeta();
		
		meta.setLore(null);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Adds {@link ItemFlag}.
	 * @param flags {@link ItemFlag} to be added.
	 * @return The item.
	 * @see ItemMeta#addItemFlags(ItemFlag...)
	 */
	public ItemBuilder addItemFlags(ItemFlag... flags) {
		ItemMeta meta = getItemMeta();

		meta.addItemFlags(flags);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Removes the given {@link ItemFlag}.
	 * @param flags {@link ItemFlag} to be removed.
	 * @return The item.
	 * @see ItemMeta#removeItemFlags(ItemFlag...)
	 */
	public ItemBuilder removeFlags(ItemFlag... flags) {
		ItemMeta meta = getItemMeta();
		
		meta.removeItemFlags(flags);
		
		_itemStack.setItemMeta(meta);
		
		return this;
	}

	/**
	 * Adds an enchantment.
	 * @param enchantment enchantment to be added.
	 * @param level level of the enchantment to be added.
	 * @return itself.
	 * @see ItemStack#addUnsafeEnchantment(Enchantment, int)
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		_itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	/**
	 * Removes an enchantment.
	 * @param enchantment enchantment to be removed.
	 * @return itself.
	 * @see ItemStack#removeEnchantment(Enchantment)
	 */
	public ItemBuilder removeEnchantment(Enchantment enchantment) {
		_itemStack.removeEnchantment(enchantment);
		return this;
	}

	/**
	 * Sets the durability.
	 * @param durability new durability.
	 * @return itself.
	 * @see ItemStack#setDurability(short)
	 */
	@SuppressWarnings("deprecation")
	public ItemBuilder setDurability(short durability) {
		_itemStack.setDurability(durability);
		return this;
	}

	/**
	 * Returns the {@link INBTTagCompound}.
	 * @return the {@link INBTTagCompound}.
	 */
	public INBTTagCompound getNBTTagCompound(){
		if(_tagCompound != null)
			_tagCompound.apply(_itemStack);
		_tagCompound = INBTTagCompound.get(_itemStack);
		return _tagCompound;
	}

	/**
	 * Returns the items meta, or throws an exception.
	 * @return The items meta.
	 */
	public ItemMeta getItemMeta() {
		ItemMeta meta = _itemStack.getItemMeta();
		Objects.requireNonNull(meta, "This item does not have meta data.");
		return meta;
	}
}
