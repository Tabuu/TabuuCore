package nl.tabuu.tabuucore.hologram;

import nl.tabuu.tabuucore.nms.wrapper.entity.IClientArmorStand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class SingleStandHologramLine extends HologramLine {

    private IClientArmorStand _stand;

    @Override
    public void destroy() {
        if(!getStand().isDestroyed())
            getStand().destroy();
    }

    @Override
    public void update(Player player) {
        getStand().sendPacketDestroy(player);
        getStand().setLocation(getLocation());
        getStand().sendPacketSpawn(player);
    }

    @Override
    public void spawn(Location location) {
        _stand = IClientArmorStand.get(location);
        _stand.setMarker(true);
        _stand.setGravity(false);
        _stand.setInvisible(true);

        setUpdating(true);
    }

    @Override
    public void setVisible(Player player, boolean visible) {
        if(visible) {
            getStand().sendPacketSpawn(player);
            getStand().sendPacketMetaData(player);
        }
        else getStand().sendPacketDestroy(player);
    }

    @Override
    public Location getLocation() {
        return _stand.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        _stand.setLocation(location);
    }

    public IClientArmorStand getStand() {
        return _stand;
    }
}