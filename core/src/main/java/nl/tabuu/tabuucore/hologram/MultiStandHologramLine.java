package nl.tabuu.tabuucore.hologram;

import nl.tabuu.tabuucore.nms.wrapper.entity.IClientArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public abstract class MultiStandHologramLine extends HologramLine {

    private Set<IClientArmorStand> _stands;

    public MultiStandHologramLine() {
        _stands = new HashSet<>();
    }

    protected Set<IClientArmorStand> getStands() {
        return _stands;
    }

    @Override
    public void destroy() {
        for(IClientArmorStand stand : _stands) {
            if(!stand.isDestroyed())
                stand.destroy();
        }
    }

    @Override
    public void update(Player player) {
        for(IClientArmorStand stand : _stands) {
            stand.sendPacketDestroy(player);
            stand.setLocation(getLocation());
            stand.sendPacketSpawn(player);
        }
    }

    @Override
    public void setVisible(Player player, boolean visible) {
        for(IClientArmorStand stand : _stands) {
            if(visible) {
                stand.sendPacketSpawn(player);
                stand.sendPacketMetaData(player);
            }
            else stand.sendPacketDestroy(player);
        }
    }
}