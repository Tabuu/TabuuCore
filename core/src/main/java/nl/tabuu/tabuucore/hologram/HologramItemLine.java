package nl.tabuu.tabuucore.hologram;

import org.bukkit.inventory.ItemStack;

public class HologramItemLine extends HologramLine {

    private ItemStack _item;

    public HologramItemLine(ItemStack item) {
        _item = item;
    }

    public ItemStack getItem() {
        return _item;
    }

    @Override
    public double getTopSpacing() {
        return 1D;
    }

    @Override
    public double getBottomSpacing() {
        return 1D;
    }
}
