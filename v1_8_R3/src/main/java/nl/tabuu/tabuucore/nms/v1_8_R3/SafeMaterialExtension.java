package nl.tabuu.tabuucore.nms.v1_8_R3;

import nl.tabuu.tabuucore.nms.wrapper.ISafeMaterialExtension;
import nl.tabuu.tabuucore.material.SafeMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SafeMaterialExtension implements ISafeMaterialExtension {

    @Override
    public ItemStack toItemStack(SafeMaterial material) {
        try{
            //TODO: spawn egg 1.8
            return new ItemStack(Material.valueOf(material.name()));
        }
        catch (IllegalArgumentException exception){
            return null;
        }
    }

}
