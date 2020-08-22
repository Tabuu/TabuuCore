package nl.tabuu.tabuucore.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.*;
import nl.tabuu.tabuucore.hologram.HologramItemLine;
import nl.tabuu.tabuucore.hologram.HologramLine;
import nl.tabuu.tabuucore.hologram.HologramStringLine;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Hologram implements IHologram {
    private List<Map.Entry<EntityArmorStand, HologramLine>> _lines;
    private Set<UUID> _viewers;
    private boolean _destroyed;

    private Location _location;
    private boolean _visible = true;

    public Hologram(Location location, HologramLine[] lines) {
        _lines = new LinkedList<>();
        _viewers = new HashSet<>();
        _destroyed = false;

        _location = location;

        setLines(lines);
    }

    @Override
    public void destroy() {
        setVisible(false);
        _lines.forEach(entry -> entry.getKey().die());
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
    public void setLines(HologramLine... lines) {
        setVisible(false);
        _lines.clear();

        double length = Arrays.stream(lines).mapToDouble(line -> line.getTopSpacing() + line.getBottomSpacing()).sum();
        Location location = getLocation().clone().add(0, length - 1.975d, 0);

        for(int i = 0; i < lines.length; i++) {
            HologramLine line = lines[i];

            if(i > 0) location.subtract(0, line.getTopSpacing(), 0);
            _lines.add(new HashMap.SimpleEntry<>(createArmorStand(location.clone()), line));
            location.subtract(0, line.getBottomSpacing(), 0);
        }

        for(Map.Entry<EntityArmorStand, HologramLine> entry : _lines) {
            HologramLine line = entry.getValue();
            EntityArmorStand stand = entry.getKey();

            if(line instanceof HologramStringLine) {
                HologramStringLine string = (HologramStringLine) line;
                stand.setCustomName(string.getString());
                stand.setCustomNameVisible(true);
            } else if (line instanceof HologramItemLine) {
                HologramItemLine item = (HologramItemLine) line;
                stand.setEquipment(4, CraftItemStack.asNMSCopy(item.getItem()));
                stand.setCustomName(" ");
            }
        }
        setVisible(true);
    }

    @Override
    public List<HologramLine> getLines() {
        return _lines.stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public void setLocation(Location location) {
        _location = location.clone();
        setLines(_lines.stream().map(Map.Entry::getValue).toArray(HologramLine[]::new));
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
        for (Map.Entry<EntityArmorStand, HologramLine> line : _lines) {
            EntityArmorStand stand = line.getKey();

            if(!isVisible()) {
                sendPacket(player, new PacketPlayOutEntityDestroy(stand.getId()));
                continue;
            }

            sendPacket(player, new PacketPlayOutSpawnEntityLiving(stand));
            sendPacket(player, new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true));

            if(line.getValue() instanceof HologramItemLine) {
                HologramItemLine itemLine = (HologramItemLine) line.getValue();
                ItemStack item = CraftItemStack.asNMSCopy(itemLine.getItem());
                sendPacket(player, new PacketPlayOutEntityEquipment(stand.getId(), 4, item));
            }
        }
    }

    private void sendPacket(Player player, Packet<PacketListenerPlayOut> packet) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    private EntityArmorStand createArmorStand(Location location) {
        if(location.getWorld() == null)
            throw new IllegalArgumentException("World cannot be null when creating a hologram.");

        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        EntityArmorStand entity = new EntityArmorStand(world, location.getX(), location.getY(), location.getZ());

        entity.setCustomNameVisible(true);
        entity.setGravity(false);
        entity.setInvisible(true);

        return entity;
    }
}
