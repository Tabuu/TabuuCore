package nl.tabuu.tabuucore.nms.v1_13_R2;

import nl.tabuu.tabuucore.nms.wrapper.ISafeMaterialExtension;
import nl.tabuu.tabuucore.material.SafeMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SafeMaterialExtension implements ISafeMaterialExtension {

    @Override
    public ItemStack toItemStack(SafeMaterial material) {
        Material bukkitMaterial = Material.valueOf(material.name());
        return new ItemStack(bukkitMaterial);
    }
}
