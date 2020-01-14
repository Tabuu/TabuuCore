package nl.tabuu.tabuucore.nms.v1_15_R1;

import net.minecraft.server.v1_15_R1.*;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Hologram implements IHologram {

    private List<EntityArmorStand> _entities;
    private Set<UUID> _viewers;
    private boolean _destroyed;

    private List<String> _lines;
    private Location _location;

    private double _lineSpacing = 0.25D;

    public Hologram(Location location, String[] lines){
        _entities = new ArrayList<>();
        _viewers = new HashSet<>();
        _destroyed = false;

        _lines = Arrays.asList(lines);
        _location = location;

        setLines(lines);
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
    public void setLines(String... lines) {
        HashSet<UUID> viewers = new HashSet<>(_viewers);
        viewers.stream().map(Bukkit::getPlayer).forEach(this::hide);

        if(lines.length > _entities.size()){
            Location location = _location.clone().subtract(0, _entities.size() * _lineSpacing, 0);
            for(int i = 0; i < lines.length - _entities.size(); i++){
                _entities.add(createArmorStand(location));
                location.subtract(0, _lineSpacing, 0);
            }
        }
        else if(lines.length < _entities.size()){
            for(int i = 0; i < _entities.size() - lines.length; i++){
                _entities.remove(_entities.size() - 1);
            }
        }

        for(int i = 0; i < lines.length; i++)
            _entities.get(i).setCustomName(new ChatComponentText(lines[i]));

        _lines = Arrays.asList(lines);
        viewers.stream().map(Bukkit::getPlayer).forEach(this::show);
    }

    @Override
    public List<String> getLines() {
        return Collections.unmodifiableList(_lines);
    }

    @Override
    public void setLocation(Location location) {
        _location = location.clone();

        for(EntityArmorStand entity : _entities) {
            entity.enderTeleportTo(location.getX(), location.getY(), location.getZ());
            location.subtract(0, _lineSpacing, 0);
        }
    }

    @Override
    public Location getLocation() {
        return _location;
    }

    @Override
    public boolean isVisible(Player player) {
        return _viewers.contains(player.getUniqueId());
    }

    private void sendPacket(Player player, Packet packet){
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private EntityArmorStand createArmorStand(Location location){
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        EntityArmorStand entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

        entity.setCustomNameVisible(true);
        entity.setInvisible(true);
        entity.setNoGravity(true);

        return entity;
    }
}
