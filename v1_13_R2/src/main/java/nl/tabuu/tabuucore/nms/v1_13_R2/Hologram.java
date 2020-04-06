package nl.tabuu.tabuucore.nms.v1_13_R2;

import net.minecraft.server.v1_13_R2.*;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Hologram implements IHologram {
    private List<EntityArmorStand> _entities;
    private Set<UUID> _viewers;
    private boolean _destroyed;

    private List<String> _lines;
    private Location _location;

    private double _lineSpacing = 0.25D;

    private boolean _visible = true;

    public Hologram(Location location, String[] lines) {
        _entities = new ArrayList<>();
        _viewers = new HashSet<>();
        _destroyed = false;

        _lines = Arrays.asList(lines);
        _location = location;

        setLines(lines);
    }

    @Override
    public void destroy() {
        setVisible(false);
        _entities.forEach(EntityArmorStand::die);
        _destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return _destroyed;
    }

    @Override
    public void removePlayer(OfflinePlayer offlinePlayer) {
        _viewers.remove(offlinePlayer.getUniqueId());

        if (!offlinePlayer.isOnline()) return;
        Player player = (Player) offlinePlayer;

        update(player);
    }

    @Override
    public void addPlayer(OfflinePlayer offlinePlayer) {
        _viewers.add(offlinePlayer.getUniqueId());

        if (!offlinePlayer.isOnline()) return;
        Player player = (Player) offlinePlayer;

        update(player);
    }

    @Override
    public void setVisible(boolean visible) {
        _visible = visible;

        for (OfflinePlayer offlinePlayer : getPlayers()) {
            if (!offlinePlayer.isOnline()) continue;
            Player player = (Player) offlinePlayer;

            update(player);
        }
    }

    @Override
    public boolean isVisible() {
        return _visible;
    }

    @Override
    public void setLines(String... lines) {
        setVisible(false);

        boolean entityOverflow = lines.length < _entities.size();
        boolean entityUnderflow = lines.length > _entities.size();

        if (entityUnderflow) {
            Location location = getLocation().subtract(0, _entities.size() * _lineSpacing, 0);
            int underflow = lines.length - _entities.size();

            for (int i = 0; i < underflow; i++) {
                _entities.add(createArmorStand(location));
                location.subtract(0, _lineSpacing, 0);
            }
        } else if (entityOverflow) {
            int overflow = _entities.size() - lines.length;

            for (int i = 0; i < overflow; i++)
                _entities.remove(_entities.size() - 1);
        }

        for (int i = 0; i < lines.length; i++) {
            EntityArmorStand stand = _entities.get(i);
            stand.setCustomName(new ChatComponentText(lines[i]));
        }

        _lines = Arrays.asList(lines);
        setVisible(true);
    }

    @Override
    public List<String> getLines() {
        return Collections.unmodifiableList(_lines);
    }

    @Override
    public void setLocation(Location location) {
        _location = location.clone();

        for (EntityArmorStand entity : _entities) {
            entity.enderTeleportTo(location.getX(), location.getY(), location.getZ());
            location.subtract(0, _lineSpacing, 0);
        }
    }

    @Override
    public Location getLocation() {
        return _location.clone();
    }

    @Override
    public List<OfflinePlayer> getPlayers() {
        return _viewers.stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toList());
    }

    private void update(Player player) {
        if (isVisible()) {
            for (EntityArmorStand entity : _entities) {
                sendPacket(player, new PacketPlayOutSpawnEntityLiving(entity));
                sendPacket(player, new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true));
            }
        } else {
            for (EntityArmorStand entity : _entities)
                sendPacket(player, new PacketPlayOutEntityDestroy(entity.getId()));
        }
    }

    private void sendPacket(Player player, Packet<PacketListenerPlayOut> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private EntityArmorStand createArmorStand(Location location) {
        if (location.getWorld() == null)
            throw new IllegalArgumentException("World cannot be null when creating a hologram.");

        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        EntityArmorStand entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

        entity.setCustomNameVisible(true);
        entity.setNoGravity(false);
        entity.setInvisible(true);
        entity.setMarker(false);

        return entity;
    }
}
