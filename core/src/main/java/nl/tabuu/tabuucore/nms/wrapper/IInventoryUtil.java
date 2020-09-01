package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents a util class for NMS inventory methods. Be warned, these methods can crash the server if not used properly.
 */
public interface IInventoryUtil {

    /**
     * Returns the next usable container id for the given player.
     * @param player the player to get the next usable container id from.
     * @return the next usable container id for the given player.
     */
    int getNextContainerId(Player player);

    void handleInventoryCloseEvent(Player player);

    /**
     * Sends a packet to the specified player to open a container with the specified id.
     * @param player the recipient of the packet.
     * @param containerId the id of the container to open.
     */
    void sendPacketOpenWindow(Player player, int containerId);

    /**
     * Sends a packet to the specified player to close a container with the specified id.
     * @param player the recipient of the packet.
     * @param containerId the id of the container to close.
     */
    void sendPacketCloseWindow(Player player, int containerId);

    /**
     * Sets the active container of a player to its default container.
     * @param player the player to set the active container of.
     */
    void setActiveContainerToDefault(Player player);

    /**
     * Sets the active container of a player to a custom container window.
     * @param player the player to set the active container of.
     * @param container the custom container to be set as active container.
     */
    void setActiveContainer(Player player, IContainerWindow container);

    /**
     * Adds a slot listener.
     * @param player The player to create the slot listener for.
     * @param container The container containing the slots to listen to.
     */
    void addActiveContainerSlotListener(Player player, IContainerWindow container);


    static IInventoryUtil get(){
        try {
            return (IInventoryUtil) NMSUtil.getWrapperClass("InventoryUtil").getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}
