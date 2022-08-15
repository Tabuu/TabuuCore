package nl.tabuu.tabuucore.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;

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

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(Location location) { }

    @Override
    public void spawn(Location location) { }

    @Override
    public void destroy() { }

    @Override
    public void update(Player player) { }

    @Override
    public void setVisible(Player player, boolean visible) { }

    @Override
    public boolean recycle(HologramLine line) {
        if(!(line instanceof HologramSpacingLine)) return false;

        HologramSpacingLine spacingLine = (HologramSpacingLine) line;
        _top = spacingLine.getTopSpacing();
        _bottom = spacingLine.getBottomSpacing();

        return true;
    }

    @Override
    public HologramLine clone() {
        return new HologramSpacingLine(getTopSpacing(), getBottomSpacing());
    }
}
