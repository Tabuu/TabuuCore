package nl.tabuu.tabuucore.hologram;

import org.bukkit.entity.Player;

import java.util.Objects;

@Deprecated
public class HologramStringLine extends SingleStandHologramLine {
    private String _string;

    public HologramStringLine(String string) {
        _string = string;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        getStand().setCustomName(getString());
        getStand().setCustomNameVisible(true);
    }

    @Override
    public boolean recycle(HologramLine line) {
        if(!(line instanceof HologramStringLine)) return false;

        HologramStringLine stringLine = (HologramStringLine) line;
        _string = stringLine.getString();

        return true;
    }

    public String getString() {
        return _string;
    }

    @Override
    public double getTopSpacing() {
        return 0.125D;
    }

    @Override
    public double getBottomSpacing() {
        return 0.125D;
    }

    @Override
    public HologramLine clone() {
        return new HologramStringLine(getString());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof HologramStringLine)) return false;
        if (!super.equals(object)) return false;
        HologramStringLine that = (HologramStringLine) object;
        return getString().equals(that.getString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getString());
    }
}
