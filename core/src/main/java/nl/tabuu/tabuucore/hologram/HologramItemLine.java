package nl.tabuu.tabuucore.hologram;

import nl.tabuu.tabuucore.nms.EnumItemSlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class HologramItemLine extends SingleStandHologramLine {

    private ItemStack _item;

    public HologramItemLine(ItemStack item) {
        _item = item;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        getStand().setEquipment(EnumItemSlot.HEAD, getItem());
    }

    @Override
    public boolean recycle(HologramLine line) {
        if(!(line instanceof HologramItemLine)) return false;

        HologramItemLine itemLine = (HologramItemLine) line;
        _item = itemLine.getItem();

        return true;
    }

    @Override
    public void setVisible(Player player, boolean visible) {
        super.setVisible(player, visible);

        if(visible) getStand().sendPacketEquipment(player);
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
    public HologramLine clone() {
        return new HologramItemLine(getItem());
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
