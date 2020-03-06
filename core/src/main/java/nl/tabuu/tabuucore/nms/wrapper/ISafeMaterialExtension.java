package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.material.SafeMaterial;
import nl.tabuu.tabuucore.nms.NMSVersion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

public interface ISafeMaterialExtension {

    /**
     * Converts a {@link SafeMaterial} to an {@link ItemStack}.
     * @param material the {@link SafeMaterial} to be converted.
     * @return an item based on a {@link SafeMaterial}.
     */
    ItemStack toItemStack(SafeMaterial material);

    /**
     * Converts an {@link ItemStack} to a {@link SafeMaterial}.
     * @param item the item to be converted.
     * @return a material based on an {@link ItemStack}.
     */
    default SafeMaterial fromItemStack(ItemStack item) {
        try {
            return SafeMaterial.valueOf(item.getType().name());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not find material.");
        }
    }

    /**
     * Converts an {@link Material} to a {@link SafeMaterial}.
     * @param material the material to be converted.
     * @return a material based on an {@link Material}.
     */
    default SafeMaterial fromMaterial(Material material) {
        for(SafeMaterial safeMaterial : SafeMaterial.values()){
            try {
                if(safeMaterial.toItemStack().getType().equals(material))
                    return safeMaterial;
            } catch (UnsupportedOperationException ignore) { }
        }

        throw new IllegalArgumentException("Could not find material.");
    }

    /**
     * Returns the SafeMaterialExtension wrapper class of the server NMS version.
     * @return the SafeMaterialExtension wrapper class of the server NMS version.
     */
    static ISafeMaterialExtension get(){
        try {
            if(NMSUtil.getVersion().isPreOrEquals(NMSVersion.v1_12_R1))
                return (ISafeMaterialExtension) NMSUtil.getWrapperClass("SafeMaterialExtension", NMSVersion.v1_12_R1).getConstructor().newInstance();
            else
                return (ISafeMaterialExtension) NMSUtil.getWrapperClass("SafeMaterialExtension").getConstructor().newInstance();
        }
        catch (ReflectiveOperationException e) {
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}
