package nl.tabuu.tabuucore.nms.wrapper;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface IHologram {

    /**
     * Destroys this hologram.
     */
    void destroy();

    /**
     * Returns whether or not this hologram has been destroyed.
     * @return whether or not this hologram has been destroyed.
     */
    boolean isDestroyed();

    /**
     * Hides this hologram for the specified player.
     * @param player the player this hologram will be hidden from.
     */
    void hide(Player player);

    /**
     * Shows this hologram to the specified player.
     * @param player the player this hologram will be showed to.
     */
    void show(Player player);

    /**
     * Sets the lines of the hologram.
     * @param lines the lines to be set.
     */
    void setLines(String... lines);

    /**
     * Returns the lines of this hologram.
     * @return the lines of this hologram.
     */
    List<String> getLines();

    /**
     * Sets the location of this hologram.
     * @param location
     */
    void setLocation(Location location);

    /**
     * Returns the location of this hologram.
     * @return the location of this hologram.
     */
    Location getLocation();

    /**
     * Returns whether or not this hologram is visible to the specified player.
     * @param player the player to check.
     * @return whether or not this hologram is visible to the specified player.
     */
    boolean isVisible(Player player);
}
