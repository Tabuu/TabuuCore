package nl.tabuu.tabuucore.hologram;

import java.util.Objects;

public class HologramStringLine extends HologramLine {
    private String _string;

    public HologramStringLine(String string) {
        _string = string;
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
