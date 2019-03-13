package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.material.SafeMaterial;
import nl.tabuu.tabuucore.nms.NMSVersion;
import org.bukkit.inventory.ItemStack;

public interface ISafeMaterialExtension {
    ItemStack toItemStack(SafeMaterial material);

    static ISafeMaterialExtension get(){
        try {
            if(NMSUtil.getVersion().isPre(NMSVersion.v1_13_R1))
                return (ISafeMaterialExtension) NMSUtil.getWrapperClass("SafeMaterialExtension", NMSVersion.v1_12_R1).getConstructor().newInstance();
            else
                return (ISafeMaterialExtension) NMSUtil.getWrapperClass("SafeMaterialExtension").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }
}
