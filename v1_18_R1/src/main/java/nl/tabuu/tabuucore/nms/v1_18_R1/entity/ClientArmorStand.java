package nl.tabuu.tabuucore.nms.v1_18_R1.entity;

import com.mojang.datafixers.util.Pair;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3D;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.wrapper.entity.IClientArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
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
        _handle.ag();
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
        org.bukkit.World world = Bukkit.getWorld(getHandle().cA().toString());
        Vec3D position = getHandle().cV();
        double x = position.a();
        double y = position.b();
        double z = position.c();
        return new Location(world, x, y, z);
    }

    @Override
    public void setLocation(Location location) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        getHandle().b(x, y, z);
    }

    @Override
    public void setCustomName(net.md_5.bungee.api.chat.BaseComponent[] displayName) {
        String data = ComponentSerializer.toString(displayName);
        IChatBaseComponent component = IChatBaseComponent.ChatSerializer.a(data);
        getHandle().a(component);
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        getHandle().n(visible);
    }

    @Override
    public void setGravity(boolean gravity) {
        getHandle().e(!gravity);
    }

    @Override
    public void setInvisible(boolean invisible) {
        getHandle().j(invisible);
    }

    @Override
    public void setMarker(boolean marker) {
        getHandle().t(marker);
    }

    @Override
    public void sendPacketDestroy(Player player) {
        sendPacket(player, new PacketPlayOutEntityDestroy(getHandle().ae()));
    }

    @Override
    public void sendPacketSpawn(Player player) {
        sendPacket(player, new PacketPlayOutSpawnEntityLiving(getHandle()));
    }

    @Override
    public void sendPacketMetaData(Player player) {
        sendPacket(player, new PacketPlayOutEntityMetadata(getHandle().ae(), getHandle().ai(), true));
    }

    @Override
    public void sendPacketEquipment(Player player) {
        List<Pair<EnumItemSlot, ItemStack>> equipment = new ArrayList<>();

        for(EnumItemSlot slot : EnumItemSlot.values()) {
            ItemStack item = _equipment.getOrDefault(slot, DEFAULT_EQUIPMENT);
            equipment.add(new Pair<>(slot, item));
        }

        sendPacket(player, new PacketPlayOutEntityEquipment(_handle.ae(), equipment));
    }

    public void sendPacket(Player player, Packet<PacketListenerPlayOut> packet) {
        ((CraftPlayer) player).getHandle().b.a(packet);
    }
}