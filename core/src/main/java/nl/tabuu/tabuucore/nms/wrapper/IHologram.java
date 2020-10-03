package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.hologram.HologramLine;
import nl.tabuu.tabuucore.hologram.HologramStringLine;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
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
     * Un-subscribes a player from this hologram, making it no longer visible to that player.
     * @param player the player that will be un-subscribed from the hologram.
     */
    void removePlayer(OfflinePlayer player);

    /**
     * Un-subscribes all players from this hologram, making it no longer visible.
     */
    default void removeAll() {
        getPlayers().forEach(this::removePlayer);
    }

    /**
     * Subscribes a player to this hologram, making it visible to that player.
     * @param player the player that will be subscribed to the hologram.
     */
    void addPlayer(OfflinePlayer player);

    /**
     * Set if the hologram is visible to its subscribers.
     * @param visible visible status.
     */
    void setVisible(boolean visible);

    /**
     * Checks if the hologram is visible to its subscribers.
     * @return true if the hologram is visible to its subscribers, else false.
     */
    boolean isVisible();

    /**
     * Sets the lines of the hologram.
     * @param lines the lines to be set.
     */
    default void setLines(String... lines) {
        setLines(Arrays.stream(lines).map(HologramStringLine::new).toArray(HologramLine[]::new));
    }

    /**
     * Sets the lines of the hologram.
     * @param lines the lines to be set.
     */
    void setLines(HologramLine... lines);

    /**
     * Returns the lines of this hologram.
     * @return the lines of this hologram.
     */
    List<HologramLine> getLines();

    /**
     * Sets the location of this hologram.
     * @param location new location of this hologram.
     */
    void setLocation(Location location);

    /**
     * Returns the location of this hologram.
     * @return the location of this hologram.
     */
    Location getLocation();

    boolean isGlobal();

    void setGlobal(boolean global);

    /**
     * Returns a list of players that are subscribed to this hologram.
     * @return A list of players that are subscribed to this hologram.
     */
    List<OfflinePlayer> getPlayers();
}
