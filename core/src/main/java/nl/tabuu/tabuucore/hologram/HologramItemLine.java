package nl.tabuu.tabuucore.hologram;

import org.bukkit.inventory.ItemStack;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof HologramItemLine)) return false;
        if (!super.equals(object)) return false;
        HologramItemLine that = (HologramItemLine) object;
        return getItem().equals(that.getItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getItem());
    }
}
