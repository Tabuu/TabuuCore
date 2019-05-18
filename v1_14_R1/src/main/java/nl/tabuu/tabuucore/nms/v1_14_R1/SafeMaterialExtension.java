package nl.tabuu.tabuucore.nms.v1_14_R1;

import nl.tabuu.tabuucore.material.SafeMaterial;
import nl.tabuu.tabuucore.nms.wrapper.ISafeMaterialExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SafeMaterialExtension implements ISafeMaterialExtension {

    @Override
    public ItemStack toItemStack(SafeMaterial material) {

        switch (material){
            case CACTUS_GREEN:
                material = SafeMaterial.GREEN_DYE;
                break;

            case DANDELION_YELLOW:
                material = SafeMaterial.YELLOW_DYE;
                break;

            case ROSE_RED:
                material = SafeMaterial.RED_DYE;
                break;

            case SIGN:
                material = SafeMaterial.OAK_SIGN;
                break;

            case WALL_SIGN:
                material = SafeMaterial.OAK_WALL_SIGN;
                break;
        }

        Material bukkitMaterial = Material.valueOf(material.name());
        return new ItemStack(bukkitMaterial);
    }

}
