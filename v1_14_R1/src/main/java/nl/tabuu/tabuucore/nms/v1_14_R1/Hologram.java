package nl.tabuu.tabuucore.nms.v1_14_R1;

import net.minecraft.server.v1_14_R1.*;
import nl.tabuu.tabuucore.nms.wrapper.hologram.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Hologram implements IHologram {

    private List<EntityArmorStand> _entities;
    private Set<UUID> _viewers;
    private boolean _destroyed;

    public Hologram(Location location, String[] lines){
        _entities = new ArrayList<>();
        _viewers = new HashSet<>();
        _destroyed = false;

        for(String line : lines){
            WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
            EntityArmorStand entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

            entity.setCustomName(new ChatComponentText(line));
            entity.setCustomNameVisible(true);
            entity.setInvisible(true);
            entity.setNoGravity(true);

            _entities.add(entity);
            location.subtract(0, 0.25D, 0);
        }
    }

    @Override
    public void destroy() {
        new HashSet<>(_viewers).stream().map(Bukkit::getPlayer).forEach(this::hide);
        _entities.forEach(EntityArmorStand::die);

        _destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return _destroyed;
    }

    @Override
    public void hide(Player player) {
        for(EntityArmorStand entity : _entities){
            sendPacket(player, new PacketPlayOutEntityDestroy(entity.getId()));
        }

        _viewers.remove(player.getUniqueId());
    }

    @Override
    public void show(Player player) {
        for(EntityArmorStand entity : _entities){
            sendPacket(player, new PacketPlayOutSpawnEntityLiving(entity));
        }

        _viewers.add(player.getUniqueId());
    }

    @Override
    public boolean isVisible(Player player) {
        return _viewers.contains(player.getUniqueId());
    }

    private void sendPacket(Player player, Packet packet){
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
