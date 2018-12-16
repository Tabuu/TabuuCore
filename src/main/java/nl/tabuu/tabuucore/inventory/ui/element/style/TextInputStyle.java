package nl.tabuu.tabuucore.inventory.ui.element.style;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TextInputStyle extends Style {

    ItemStack _renameItem;
    String _placeHolder;

    public TextInputStyle(Material enabled, Material disabled, Material renameItem, String placeHolder) {
        this(new ItemStack(enabled), new ItemStack(disabled), new ItemStack(renameItem), placeHolder);
    }

    public TextInputStyle(ItemStack enabled, ItemStack disabled, ItemStack renameItem, String placeHolder) {
        super(enabled, disabled);
        _renameItem = renameItem;
        _placeHolder = placeHolder;
    }

    public String getPlaceHolder(){
        return _placeHolder;
    }

    public ItemStack getRenameItem(){
        return _renameItem;
    }
}
