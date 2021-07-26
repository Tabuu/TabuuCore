package nl.tabuu.tabuucore.inventory.ui.element.style;

import nl.tabuu.tabuucore.item.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TextInputStyle extends Style {

    private ItemStack _renameItem;
    private String _placeHolder;

    public TextInputStyle(ItemStack enabled, ItemStack disabled, ItemStack renameItem, String placeHolder) {
        super(enabled, disabled);
        _renameItem = renameItem;
        _placeHolder = placeHolder;
    }

    public TextInputStyle(ItemStack display, ItemStack renameItem, String placeHolder) {
        this(display, display, renameItem, placeHolder);
    }

    public TextInputStyle(ItemBuilder enabled, ItemBuilder disabled, ItemBuilder renameItem, String placeHolder) {
        this(enabled.build(), disabled.build(), renameItem.build(), placeHolder);
    }

    public TextInputStyle(ItemBuilder display, ItemBuilder renameItem, String placeHolder) {
        this(display, display, renameItem, placeHolder);
    }

    public TextInputStyle(Material enabled, Material disabled, Material renameItem, String placeHolder) {
        this(new ItemStack(enabled), new ItemStack(disabled), new ItemStack(renameItem), placeHolder);
    }

    public TextInputStyle(Material display, Material renameItem, String placeHolder) {
        this(display, display, renameItem, placeHolder);
    }

    public TextInputStyle(XMaterial enabled, XMaterial disabled, XMaterial renameItem, String placeHolder) {
        this(enabled.parseItem(), disabled.parseItem(), renameItem.parseItem(), placeHolder);
    }

    public TextInputStyle(XMaterial display, XMaterial renameItem, String placeHolder) {
        this(display, display, renameItem, placeHolder);
    }

    public String getPlaceHolder(){
        return _placeHolder;
    }

    public ItemStack getRenameItem(){
        return _renameItem;
    }
}
