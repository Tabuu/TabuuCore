package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;
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

    static IAnvilUtil get(){
        try {
            return (IAnvilUtil) NMSUtil.getWrapperClass("AnvilUtil").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            TabuuCore.getInstance().getLogger().severe("No AnvilUtil found for your Minecraft version!");
        }

        return null;
    }
}
