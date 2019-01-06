package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.util.SafeMaterial;
import org.bukkit.inventory.ItemStack;

public interface ISafeMaterialExtension {
    ItemStack toItemStack(SafeMaterial material);

    static ISafeMaterialExtension get(){
        try {
            return (ISafeMaterialExtension) NMSUtil.getWrapperClass("SafeMaterialExtension").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }
}
