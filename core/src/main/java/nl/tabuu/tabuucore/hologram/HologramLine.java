package nl.tabuu.tabuucore.hologram;

import java.util.Objects;

public abstract class HologramLine {

    private double _pitch = 0d, _yaw = 0d;
    private boolean _updating = true;

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

    /**
     * Returns the rotation of the line on the y-axis (yaw).
     * @return The rotation of the line on the y-axis (yaw).
     */
    public double getYaw() { return _yaw; }

    /**
     * Sets the rotation of the hologram line on the y-axis (yaw).
     * @param yaw The rotation on the y-axis (yaw).
     */
    public void setYaw(double yaw) { _yaw = yaw; }

    /**
     * Returns the rotation of the line on the x-axis (pitch).
     * @return The rotation of the line on the x-axis (pitch).
     */
    public double getPitch() { return _pitch; }

    /**
     * Sets the rotation of the hologram line on the x-axis (pitch).
     * @param pitch The rotation on the x-axis (pitch).
     */
    public void setPitch(double pitch) { _pitch = pitch; }

    public boolean isUpdating() { return _updating; }

    public void setUpdating(boolean updating) { _updating = updating; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof HologramLine)) return false;
        HologramLine line = (HologramLine) object;
        return Double.compare(line.getPitch(), getPitch()) == 0 &&
                Double.compare(line.getYaw(), getYaw()) == 0 &&
                Double.compare(line.getBottomSpacing(), getBottomSpacing()) == 0 &&
                Double.compare(line.getTopSpacing(), getTopSpacing()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPitch(), getYaw(), getBottomSpacing(), getTopSpacing());
    }
}
