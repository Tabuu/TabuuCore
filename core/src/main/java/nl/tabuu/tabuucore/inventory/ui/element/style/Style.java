package nl.tabuu.tabuucore.inventory.ui.element.style;

import nl.tabuu.tabuucore.item.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Style {

    private ItemStack _enabled, _disabled;

    public Style(ItemStack enabled, ItemStack disabled) {
        _enabled = enabled;
        _disabled = disabled;
    }

    public Style(ItemStack display) {
        this(display, display);
    }

    public Style(ItemBuilder enabled, ItemBuilder disabled) {
        this(enabled.build(), disabled.build());
    }

    public Style(ItemBuilder display) {
        this(display, display);
    }

    public Style(Material enabled, Material disabled) {
        this(new ItemStack(enabled), new ItemStack(disabled));
    }

    public Style(Material display) {
        this(display, display);
    }

    public Style(XMaterial enabled, XMaterial disabled) {
        this(enabled.parseItem(), disabled.parseItem());
    }

    public Style(XMaterial display) {
        this(display, display);
    }

    public ItemStack getEnabled() {
        return _enabled;
    }

    public void setEnabled(ItemStack itemStack) {
        _enabled = itemStack;
    }

    public ItemStack getDisabled() {
        return _disabled;
    }

    public void setDisabled(ItemStack itemStack) {
        _disabled = itemStack;
    }
}
