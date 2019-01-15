package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.material.SafeMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Arrays;

public class DebugTrigger implements Listener {

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event){
        Player player = event.getPlayer();

        SafeMaterial[] materials = new SafeMaterial[]{
                SafeMaterial.CREEPER_SPAWN_EGG,
                SafeMaterial.HORSE_SPAWN_EGG
        };

        Arrays.stream(materials).forEach(material -> player.getInventory().addItem(material.toItemStack()));
    }

}
