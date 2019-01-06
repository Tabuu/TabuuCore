package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.InvocationTargetException;

public interface IAnvilUtil {

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

    static IAnvilUtil get(){
        try {
            return (IAnvilUtil) NMSUtil.getWrapperClass("AnvilUtil").getConstructor().newInstance();
        } catch (Exception ignored) { }

        return null;
    }
}
