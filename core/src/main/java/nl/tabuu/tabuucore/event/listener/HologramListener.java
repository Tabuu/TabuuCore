package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.api.HologramAPI;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class HologramListener implements Listener {
    private HologramAPI _api;

    public HologramListener() {
        _api = HologramAPI.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (IHologram hologram : _api.getHolograms()) {
            if (hologram.getPlayers().contains(player))
                hologram.show(player);
        }
    }
}
