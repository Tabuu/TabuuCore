package nl.tabuu.tabuucore.hologram;

import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Hologram implements IHologram {
    private Set<UUID> _viewers;
    private Location _location;
    private List<HologramLine> _lines;
    private boolean _destroyed, _global, _visible;

    public Hologram(Set<UUID> viewers, Location location, List<HologramLine> lines, boolean destroyed, boolean global, boolean visible) {
        _viewers = new HashSet<>(viewers);
        _location = location.clone();
        _lines = new ArrayList<>(lines);
        _destroyed = destroyed;
        _global = global;
        _visible = visible;
    }

    public Hologram(Location location, HologramLine... lines) {
        this(
                Collections.emptySet(),
                location,
                Arrays.asList(lines),
                false,
                false,
                true
        );
    }

    public void update(HologramLine line, Player player) {
        line.setVisible(player, false);
        if(!isVisible()) return;

        line.update(player);

        line.setVisible(player, true);
    }

    public void update(Player player) {
        for(HologramLine line : getLines())
            update(line, player);
    }

    public void update(boolean force) {
        for(HologramLine line : getLines()) {
            if(!line.isUpdating() && !force) continue;
            for(OfflinePlayer offlinePlayer : isGlobal() ? getPlayers() : Bukkit.getOnlinePlayers()) {
                if(!offlinePlayer.isOnline()) continue;
                Player player = (Player) offlinePlayer;
                update(line, player);
            }

            line.setUpdating(false);
        }
    }

    public void update() {
        update(false);
    }

    public void addPlayer(OfflinePlayer player) {
        _viewers.add(player.getUniqueId());

        if(player.isOnline())
            _lines.forEach(line -> line.setVisible((Player) player, true));
    }

    public void removePlayer(OfflinePlayer player) {
        _viewers.remove(player.getUniqueId());

        if(player.isOnline())
            _lines.forEach(line -> line.setVisible((Player) player, false));
    }

    public void removeAll() {
        getPlayers().forEach(this::removePlayer);
    }

    public List<OfflinePlayer> getPlayers() {
        return _viewers.stream().map(Bukkit::getOfflinePlayer).collect(Collectors.toList());
    }

    public List<HologramLine> getLines() {
        return Collections.unmodifiableList(_lines);
    }

    public void setLines(HologramLine... lines) {
        if(lines.length == 0) {
            setVisible(false);
            _lines.forEach(HologramLine::destroy);
            _lines.clear();
            setVisible(true);

            return;
        }

        // Synchronizing new instances with old instances
        for(int i = 0; i < lines.length || i < _lines.size(); i++) {
            if(i >= lines.length) {
                HologramLine line = _lines.remove(i);
                line.destroy();
                continue;
            }

            HologramLine newLine = lines[i];

            if(i < _lines.size()) {
                HologramLine oldLine = _lines.get(i);

                if(!oldLine.recycle(newLine))
                    _lines.set(i, newLine);

            } else {
                newLine.spawn(getLocation());
                _lines.add(newLine);
            }
        }

        // Calculating hologram size, and start location
        double size = Arrays.stream(lines).mapToDouble(line -> line.getTopSpacing() + line.getBottomSpacing()).sum();
        Location location = getLocation().clone().add(0, size - 1.975d, 0);

        // Matching ArmorStands with lines
        for(int i = 0; i < _lines.size(); i++) {
            HologramLine line = _lines.get(i);

            if(i > 0) location.subtract(0, line.getTopSpacing(), 0);
            line.setLocation(location);
            location.subtract(0, line.getBottomSpacing(), 0);
        }

        update();
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        _location = location;
    }

    public void destroy() {
        _lines.forEach(HologramLine::destroy);
        _destroyed = true;
    }

    public boolean isDestroyed() {
        return _destroyed;
    }

    public boolean isGlobal() {
        return _global;
    }

    public void setGlobal(boolean global) {
        _global = global;
    }

    public boolean isVisible() {
        return _visible;
    }

    public void setVisible(boolean visible) {
        _visible = visible;
    }
}