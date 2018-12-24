package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.debug.DebugCommand;
import nl.tabuu.tabuucore.debug.DebugListener;
import nl.tabuu.tabuucore.event.listener.InventoryListener;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIManager;
import nl.tabuu.tabuucore.plugin.TabuuCorePlugin;
import org.bukkit.Bukkit;
import nl.tabuu.tabuucore.metrics.bstats.Metrics;
import nl.tabuu.tabuucore.metrics.massivestats.MassiveStats;

public class TabuuCore extends TabuuCorePlugin {

    private static TabuuCore _instance;
    private InventoryUIManager _inventoryUIManager;

    @Override
    public void onEnable(){
        _instance = this;

        getInstance().getLogger().info("Enabling TabuuCore...");

        // Registering configuration.
        getConfigurationManager().addConfiguration("config");
        getConfigurationManager().addConfiguration("lang");

        // Inventory user interface related.
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());
        _inventoryUIManager = new InventoryUIManager();

        // Metrics.
        new Metrics(this);
        new MassiveStats(this);

        // Debug purposes.
        Bukkit.getPluginManager().registerEvents(new DebugListener(), getInstance());
        this.getCommand("tabuucore").setExecutor(new DebugCommand());
    }

    @Override
    public void onDisable(){
        getInstance().getLogger().info("Disabling TabuuCore...");
    }

    public InventoryUIManager getInventoryUIManager(){
        return _inventoryUIManager;
    }

    public static TabuuCore getInstance(){
        return _instance;
    }
}
