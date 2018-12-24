package nl.tabuu.tabuucore.inventory.ui.element.style;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Style {

    private ItemStack _enabled, _disabled;

    public Style(Material enabled, Material disabled){
        this(new ItemStack(enabled), new ItemStack(disabled));
    }

    public Style(ItemStack enabled, ItemStack disabled){
        _enabled = enabled;
        _disabled = disabled;
    }

    public ItemStack getEnabled(){
        return _enabled;
    }

    public void setEnabled(ItemStack itemStack){
        _enabled = itemStack;
    }

    public ItemStack getDisabled(){
        return _disabled;
    }

    public void setDisabled(ItemStack itemStack){
        _disabled = itemStack;
    }
}
