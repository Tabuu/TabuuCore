package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.api.HologramAPI;
import nl.tabuu.tabuucore.hologram.Hologram;
import org.bukkit.Bukkit;
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

        for (Hologram hologram : _api.getHolograms()) {
            if (hologram.getPlayers().contains(player) || hologram.isGlobal())
                hologram.addPlayer(player);
        }

        if(Bukkit.getOnlinePlayers().size() == 1) _api.updateHolograms();
    }
}
