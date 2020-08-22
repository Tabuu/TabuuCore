package nl.tabuu.tabuucore.hologram;

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
}
