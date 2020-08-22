package nl.tabuu.tabuucore.hologram;

public class HologramSpacingLine extends HologramLine {

    private double _top, _bottom;

    public HologramSpacingLine(double top, double bottom) {
        _top = top;
        _bottom = bottom;
    }

    @Override
    public double getTopSpacing() {
        return _top;
    }

    @Override
    public double getBottomSpacing() {
        return _bottom;
    }
}
