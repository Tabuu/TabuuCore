package nl.tabuu.tabuucore.nms.wrapper.container;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;

public interface IContainerWindow {

    Player getPlayer();

    int getWindowId();

    void open();

    void close();

    Inventory getInventory();

    Object getNMSContainer();

    static <T extends IContainerWindow> T get(Player player, Class<T> type){
        try {
            String className = type.getSimpleName().substring(1);
            Class wrapper = NMSUtil.getWrapperClass("container." + className);

            Constructor constructor = wrapper.getDeclaredConstructor(Player.class);
            constructor.setAccessible(true);

            return (T) constructor.newInstance(player);

            // return (T) NMSUtil.getWrapperClass("container." + className).getConstructor(Player.class).newInstance(player);
        }
        catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}
