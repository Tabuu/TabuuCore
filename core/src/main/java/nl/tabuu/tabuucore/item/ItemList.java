package nl.tabuu.tabuucore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class ItemList extends ArrayList<ItemStack>{

    public ItemList(){}

    public ItemList(Collection<ItemStack> stacks){
        this();
        addAll(stacks);
    }

    public void addAll(ItemStack... stacks){
        for(ItemStack stack : stacks)
            add(stack);
    }

    /**
     * Re-stacks all items of this list using {@link #stackAll(Collection this)}
     */
    public void squash(){
        ItemList clone = this.clone();
        this.clear();
        this.stackAll(clone);
    }

    /**
     * Returns the product of all {@link ItemStack#getAmount()}.
     * @return
     */
    public int itemCount(){
        int count = 0;
        for(ItemStack itemStack : this)
            count += itemStack.getAmount();

        return count;
    }

    /**
     * This method will add the given {@link ItemStack} to the ItemList in an inventory stacking fashion.
     * The method will first try to add all items to existing {@link ItemStack}s before adding new items to the list.
     * @param stack ItemStack to be added.
     * @return      an ItemStack containing the items that the list had to create a new entry for, else null.
     *              If the amount of the ItemStack provided exceeds the maximum ItemStack size the ItemStack will NOT be spit up.
     */
    public ItemStack stack(ItemStack stack){
        if(stack == null || stack.getType().equals(Material.AIR) || stack.getAmount() < 1)
            return null;

        ItemStack itemToAdd = stack.clone();
        int count = itemToAdd.getAmount();

        for(int i = 0; i < this.size(); i++){
            ItemStack item = this.get(i);
            if(item == null || item.getType().equals(Material.AIR)) {
                ItemStack itemClone = itemToAdd.clone();
                itemClone.setAmount(count);
                this.set(i, itemClone);
                count = 0;
            }
            else if(item.isSimilar(itemToAdd)){
                int fitting = item.getMaxStackSize() - item.getAmount();
                if(fitting > 0){
                    if(itemToAdd.getAmount() >= fitting){
                        item.setAmount(item.getAmount() + fitting);
                        count -= fitting;
                    }
                    else{
                        item.setAmount(item.getAmount() + itemToAdd.getAmount());
                        count -= itemToAdd.getAmount();
                    }
                }
            }

            if(count <= 0)
                break;
        }
        if(count > 0){
            itemToAdd.setAmount(count);
            this.add(itemToAdd);
            return itemToAdd;
        }
        return null;
    }

    /**
     * Stacks all provided items with {@link #stack(ItemStack itemStack)}
     * @param stacks ItemStacks to be stacked.
     * @return List of items that could not fit.
     */
    public ItemList stackAll(ItemStack... stacks){
        ItemList itemList = new ItemList();
        for (ItemStack stack : stacks)
            itemList.stack(stack(stack));
        return itemList;
    }

    /**
     * Stacks all provided items with {@link #stack(ItemStack itemStack)}
     * @param stacks ItemStacks to be stacked.
     * @return Self.
     */
    public ItemList stackAll(Collection<ItemStack> stacks){
        return stackAll(stacks.stream().toArray(ItemStack[]::new));
    }

    /**
     * Removes ItemStacks for which {@link ItemStack#isSimilar(ItemStack providedItemStack)} is true. The maximum amount of items to be removed is the specified ItemStacks {@link ItemStack#getAmount()}.
     * @param stack The ItemStack to be removed.
     * @return The residue of the ItemStack that could not have been removed.
     */
    public ItemStack remove(ItemStack stack){
        if(stack == null || stack.getType().equals(Material.AIR))
            return null;

        ItemStack itemToRemove = stack.clone();
        int count = itemToRemove.getAmount();

        for(int i = 0; i < this.size(); i++){
            ItemStack item = this.get(i);
            if(item == null || item.getType().equals(Material.AIR))
                continue;
            else if(item.isSimilar(itemToRemove)){
                if(item.getAmount() > count){
                    item.setAmount(item.getAmount() - count);
                    count = 0;
                }
                else if(item.getAmount() <= count){
                    this.set(i, null);
                    count -= item.getAmount();
                }
            }

            if(count <= 0)
                break;
        }
        itemToRemove.setAmount(count);
        return itemToRemove;
    }

    /**
     * Removes all provided items with {@link #stack(ItemStack itemStack)}
     * @param stacks ItemStacks to be removed.
     * @return Self.
     */
    public ItemList remove(ItemStack... stacks){
        ItemList nonRemovedStacks = new ItemList();
        for(int i = 0; i < stacks.length; i++)
            nonRemovedStacks.stack(remove(stacks[i])); // editing stack while looping?
        return nonRemovedStacks;
    }

    /**
     * Removes all provided items with {@link #stack(ItemStack itemStack)}
     * @param stacks ItemStacks to be removed.
     * @return Self.
     */
    public ItemList remove(Collection<ItemStack> stacks){
        return remove(stacks.stream().toArray(ItemStack[]::new));
    }

    public int remove(Material material, int amount){
        for(ItemStack item : this){
            if(amount <= 0) break;
            if(item == null || item.getType().equals(Material.AIR)) continue;
            if(item.getType().equals(material)){
                if(item.getAmount() >= amount) {
                    int toRemove = amount;
                    amount -= item.getAmount();
                    item.setAmount(item.getAmount() - toRemove);
                }
                else{
                    amount -= item.getAmount();
                    item.setAmount(0);
                }
            }
        }
        return amount;
    }

    public ItemList getFirst(int amount){
        ItemList output = new ItemList();
        int count = amount;
        for(ItemStack item : this){
            if(item == null || item.getType().equals(Material.AIR))
                continue;

            if(item.getAmount() <= count){
                output.add(item.clone());
                count -= item.getAmount();
            }
            else{
                ItemStack clone = item.clone();
                clone.setAmount(count);
                output.add(clone);
                count = 0;
            }
            if(amount <= 0)
                break;
        }
        return output;
    }

    @Override
    public ItemList clone(){
        ItemList clone = new ItemList();
        this.forEach(i -> {
            if(i == null)
                clone.add(null);
            else
                clone.add(i.clone());
        });
        return clone;
    }
}
