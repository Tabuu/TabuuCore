package nl.tabuu.tabuucore.debug;

import nl.tabuu.tabuucore.debug.TestUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class DebugListener implements Listener {

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(event.isSneaking())
            new TestUI().open(event.getPlayer());
    }

}
