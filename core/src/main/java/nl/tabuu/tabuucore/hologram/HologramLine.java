package nl.tabuu.tabuucore.hologram;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class HologramLine implements Cloneable {

    private boolean _updating = true;

    public abstract void destroy();

    public abstract void update(Player player);

    public abstract void spawn(Location location);

    public abstract void setVisible(Player player, boolean visible);

    public abstract boolean recycle(HologramLine line);

    /**
     * Returns the distance between this line and the line above minus that line's bottom-spacing.
     * @return The distance between this line and the line above minus that line's bottom-spacing.
     */
    public abstract double getTopSpacing();

    /**
     * Returns the distance between this line and the line bellow minus that line's top-spacing.
     * @return The distance between this line and the line bellow minus that line's top-spacing.
     */
    public abstract double getBottomSpacing();

    public abstract Location getLocation();

    public abstract void setLocation(Location location);

    public boolean isUpdating() { return _updating; }

    public void setUpdating(boolean updating) { _updating = updating; }

    @Override
    public abstract HologramLine clone();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof HologramLine)) return false;
        HologramLine line = (HologramLine) object;
        return Double.compare(line.getBottomSpacing(), getBottomSpacing()) == 0 &&
                Double.compare(line.getTopSpacing(), getTopSpacing()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBottomSpacing(), getTopSpacing());
    }
}