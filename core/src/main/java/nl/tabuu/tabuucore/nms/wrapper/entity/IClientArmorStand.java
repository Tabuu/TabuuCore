package nl.tabuu.tabuucore.nms.wrapper.entity;

import net.md_5.bungee.api.chat.BaseComponent;
import nl.tabuu.tabuucore.nms.EnumItemSlot;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.text.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IClientArmorStand {

    Object getHandle();

    void destroy();

    boolean isDestroyed();

    void setEquipment(EnumItemSlot slot, ItemStack item);

    Location getLocation();

    void setLocation(Location location);

    void setCustomName(BaseComponent[] displayName);

    default void setCustomName(String displayName) {
        setCustomName(ComponentBuilder.create().thenText(displayName).build());
    }

    void setCustomNameVisible(boolean visible);

    void setGravity(boolean gravity);

    void setInvisible(boolean invisible);

    void setMarker(boolean marker);

    void sendPacketDestroy(Player player);

    void sendPacketSpawn(Player player);

    void sendPacketMetaData(Player player);

    void sendPacketEquipment(Player player);

    static IClientArmorStand get(Location location) {
        try {
            return (IClientArmorStand) NMSUtil.getWrapperClass("entity.ClientArmorStand").getConstructor(Location.class).newInstance(location);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Could not create wrapper class!");
        }
    }
}