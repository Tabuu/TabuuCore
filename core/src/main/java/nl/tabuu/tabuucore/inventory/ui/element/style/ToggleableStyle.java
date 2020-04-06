package nl.tabuu.tabuucore.inventory.ui.element.style;

import nl.tabuu.tabuucore.material.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ToggleableStyle extends Style {

    private ItemStack _on, _off;

    public ToggleableStyle(ItemStack on, ItemStack off, ItemStack disabled) {
        super(on, disabled);
        _on = on;
        _off = off;
    }

    public ToggleableStyle(Material on, Material off, Material disabled) {
        this(new ItemStack(on), new ItemStack(off), new ItemStack(disabled));
    }

    public ToggleableStyle(XMaterial on, XMaterial off, XMaterial disabled) {
        this(on.parseItem(), off.parseItem(), disabled.parseItem());
    }

    public ItemStack getOn() {
        return _on;
    }

    public ItemStack getOff() {
        return _off;
    }

}
