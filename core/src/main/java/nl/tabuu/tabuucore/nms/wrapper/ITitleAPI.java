package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface ITitleAPI {

    void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut);

    void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut);

    void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut);

    static ITitleAPI get(){
        try {
            return (ITitleAPI) NMSUtil.getWrapperClass("TitleAPI").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }
}
