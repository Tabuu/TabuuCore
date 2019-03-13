package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

public interface IInventoryUtil {

    int getNextContainerId(Player player);

    void handleInventoryCloseEvent(Player player);

    void sendPacketOpenWindow(Player player, int containerId);

    void sendPacketCloseWindow(Player player, int containerId);

    void setActiveContainerDefault(Player player);

    void setActiveContainer(Player player, Object container);

    void setActiveContainerId(Object container, int containerId);

    void addActiveContainerSlotListener(Object container, Player player);

    Inventory toBukkitInventory(Object container);

    Object newContainerAnvil(Player player);

    String getRenameText(AnvilInventory inventory);

    static IInventoryUtil get(){
        try {
            return (IInventoryUtil) NMSUtil.getWrapperClass("InventoryUtil").getConstructor().newInstance();
        } catch (Exception ignored) { }

        return null;
    }
}
