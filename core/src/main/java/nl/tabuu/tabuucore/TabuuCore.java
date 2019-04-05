package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.event.listener.InventoryListener;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIManager;
import nl.tabuu.tabuucore.metrics.bstats.Metrics;
import nl.tabuu.tabuucore.plugin.TabuuCorePlugin;
import org.bukkit.Bukkit;

public class TabuuCore extends TabuuCorePlugin {

    private static TabuuCore _instance;
    private InventoryUIManager _inventoryUIManager;

    @Override
    public void onEnable(){
        _instance = this;

        getInstance().getLogger().info("Enabling TabuuCore...");

        // Registering configuration.
        getConfigurationManager().addConfiguration("lang");

        // Inventory user interface related.
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());
        _inventoryUIManager = new InventoryUIManager();

        // Metrics.
        new Metrics(this);
        // new MassiveStats(this); disabling MassiveStats, it only causes issues.
        // TODO: Remove/Fix MassiveStats.
    }

    @Override
    public void onDisable(){
        getInstance().getLogger().info("Disabling TabuuCore...");
        getInventoryUIManager().forEach(InventoryUI::closeAll);
    }

    public InventoryUIManager getInventoryUIManager(){
        return _inventoryUIManager;
    }

    public static TabuuCore getInstance(){
        return _instance;
    }
}
