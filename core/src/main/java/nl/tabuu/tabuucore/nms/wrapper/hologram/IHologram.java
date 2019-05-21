package nl.tabuu.tabuucore.nms.wrapper.hologram;

import org.bukkit.entity.Player;

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
     * Returns whether or not this hologram is visible to the specified player.
     * @return whether or not this hologram is visible to the specified player.
     */
    boolean isVisible(Player player);
}
