package nl.tabuu.tabuucore.nms.wrapper.container;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Constructor;

public interface IContainerWindow {

    void open();

    void close();

    Player getPlayer();

    Inventory getInventory();

    @SuppressWarnings("unchecked")
    static <T extends IContainerWindow> T get(Player player, Class<T> type){
        try {
            String className = type.getSimpleName().substring(1);
            Class<T> wrapper = (Class<T>) NMSUtil.getWrapperClass("container." + className);

            Constructor<T> constructor = wrapper.getDeclaredConstructor(Player.class);
            constructor.setAccessible(true);

            return constructor.newInstance(player);
        }
        catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}
