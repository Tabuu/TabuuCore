package nl.tabuu.tabuucore.nms.v1_16_R3.entity;

import com.mojang.datafixers.util.Pair;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_16_R3.*;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.wrapper.entity.IClientArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.*;

public class ClientArmorStand implements IClientArmorStand {
    private static final ItemStack DEFAULT_EQUIPMENT = CraftItemStack.asNMSCopy(XMaterial.AIR.parseItem());

    private boolean _destroyed;
    private EntityArmorStand _handle;
    private Map<EnumItemSlot, ItemStack> _equipment;

    public ClientArmorStand(Location location) {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        _handle = new EntityArmorStand(world, x, y, z);

        _destroyed = false;
        _equipment = new HashMap<>();
    }

    @Override
    public EntityArmorStand getHandle() {
        return _handle;
    }

    @Override
    public void destroy() {
        _handle.die();
        _destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return _destroyed;
    }

    @Override
    public void setEquipment(nl.tabuu.tabuucore.nms.EnumItemSlot slot, org.bukkit.inventory.ItemStack item) {
        EnumItemSlot nmsSlot = EnumItemSlot.valueOf(slot.name());
        if(Objects.isNull(item)) _equipment.remove(nmsSlot);
        else _equipment.put(nmsSlot, CraftItemStack.asNMSCopy(item));
    }

    @Override
    public Location getLocation() {
        org.bukkit.World world = Bukkit.getWorld(getHandle().getWorld().toString());
        Vec3D position = getHandle().getPositionVector();
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        return new Location(world, x, y, z);
    }

    @Override
    public void setLocation(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        getHandle().enderTeleportTo(x, y, z);
    }

    @Override
    public void setCustomName(net.md_5.bungee.api.chat.BaseComponent[] displayName) {
        String data = ComponentSerializer.toString(displayName);
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(data);
        getHandle().setCustomName(component);
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        getHandle().setCustomNameVisible(visible);
    }

    @Override
    public void setGravity(boolean gravity) {
        getHandle().setNoGravity(!gravity);
    }

    @Override
    public void setInvisible(boolean invisible) {
        getHandle().setInvisible(invisible);
    }

    @Override
    public void setMarker(boolean marker) {
        getHandle().setMarker(marker);
    }

    @Override
    public void sendPacketDestroy(Player player) {
        sendPacket(player, new PacketPlayOutEntityDestroy(getHandle().getId()));
    }

    @Override
    public void sendPacketSpawn(Player player) {
        sendPacket(player, new PacketPlayOutSpawnEntityLiving(getHandle()));
    }

    @Override
    public void sendPacketMetaData(Player player) {
        sendPacket(player, new PacketPlayOutEntityMetadata(getHandle().getId(), getHandle().getDataWatcher(), true));
    }

    @Override
    public void sendPacketEquipment(Player player) {
        List<Pair<EnumItemSlot, ItemStack>> equipment = new ArrayList<>();

        for(EnumItemSlot slot : EnumItemSlot.values()) {
            ItemStack item = _equipment.getOrDefault(slot, DEFAULT_EQUIPMENT);
            equipment.add(new Pair<>(slot, item));
        }

        sendPacket(player, new PacketPlayOutEntityEquipment(_handle.getId(), equipment));
    }

    public void sendPacket(Player player, Packet<PacketListenerPlayOut> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}