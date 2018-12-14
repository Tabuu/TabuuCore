package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.event.listener.InventoryEventListener;
import nl.tabuu.tabuucore.plugin.TabuuCorePlugin;
import org.bukkit.Bukkit;

public class TabuuCore extends TabuuCorePlugin {

    private static TabuuCore _instance;

    @Override
    public void onEnable(){
        _instance = this;

        getConfigurationManager().addConfiguration("config");

        Bukkit.getPluginManager().registerEvents(new InventoryEventListener(), getInstance());

        Bukkit.getScheduler().runTask(this, () -> Bukkit.getOnlinePlayers().forEach(p -> new TestUI().open(p)));
    }

    @Override
    public void onDisable(){

    }

    public static TabuuCore getInstance(){
        return _instance;
    }
}
