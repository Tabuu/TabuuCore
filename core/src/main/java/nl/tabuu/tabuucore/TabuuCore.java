package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.event.listener.HologramListener;
import nl.tabuu.tabuucore.event.listener.InventoryListener;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIManager;
import nl.tabuu.tabuucore.metrics.bstats.Metrics;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.NMSVersion;
import nl.tabuu.tabuucore.api.HologramAPI;
import nl.tabuu.tabuucore.nms.wrapper.IHologram;
import nl.tabuu.tabuucore.plugin.TabuuCorePlugin;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Main class of TabuuCore.
 */
public class TabuuCore extends TabuuCorePlugin {

    private static TabuuCore _instance;
    private InventoryUIManager _inventoryUIManager;

    @Override
    public void onEnable(){
        _instance = this;

        getInstance().getLogger().info("Enabling TabuuCore...");

        // Checking for support.
        NMSVersion version = NMSUtil.getVersion();
        if(!version.isSupported()){
            getInstance().getLogger().warning("UNSUPPORTED NMS VERSION! This version of TabuuCore does not support your NMS version.");
            getInstance().getLogger().warning("Current NMS version: " + version.name());
            getInstance().getLogger().warning("Supported NMS versions: " +
                    Arrays.stream(NMSVersion.values())
                            .filter(NMSVersion::isSupported)
                            .map(Enum::name)
                            .collect(Collectors.joining(", ")));
        }

        // Registering configuration.
        getConfigurationManager().addConfiguration("lang");
        getConfigurationManager().addConfiguration("config");

        // Inventory user interface related.
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());
        _inventoryUIManager = new InventoryUIManager();

        // Hologram related.
        Bukkit.getPluginManager().registerEvents(new HologramListener(), getInstance());

        // Metrics.
        new Metrics(this);

        // Message for conflicting API versions.
        if(version.isPostOrEquals(NMSVersion.v1_13_R1)) {
            String apiVersion = getDescription().getAPIVersion();
            if (!version.getSpigotAPIVersion().equals(apiVersion)) {
                getInstance().getLogger().severe("The API version of this TabuuCore file has been set to: " + apiVersion + ", while your server is running: " + version.name());
                getInstance().getLogger().severe("Please download a version specific TabuuCore file, or edit the plugin.yml file inside the TabuuCore plugin jar (only if you have a multi version copy of TabuuCore).");
            }
        }
        getInstance().getLogger().info("TabuuCore enabled.");
    }

    @Override
    public void onDisable(){
        getInstance().getLogger().info("Disabling TabuuCore...");

        // Closes all UIs managed by TabuuCore to prevent duplication of items.
        getInventoryUIManager().forEach(InventoryUI::closeAll);

        // Destroy all holograms.
        HologramAPI.getInstance().getHolograms().forEach(IHologram::destroy);

        getInstance().getLogger().info("TabuuCore disabled.");
    }

    /**
     * Returns the {@link InventoryUIManager} used by TabuuCore.
     * @return the {@link InventoryUIManager} used by TabuuCore.
     */
    public InventoryUIManager getInventoryUIManager(){
        return _inventoryUIManager;
    }

    /**
     * Returns the instance of TabuuCore.
     * @return the instance of TabuuCore.
     */
    public static TabuuCore getInstance(){
        return _instance;
    }
}
