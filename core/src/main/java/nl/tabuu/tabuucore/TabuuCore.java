package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.api.HologramAPI;
import nl.tabuu.tabuucore.command.CommandRegister;
import nl.tabuu.tabuucore.configuration.file.YamlConfiguration;
import nl.tabuu.tabuucore.event.listener.HologramListener;
import nl.tabuu.tabuucore.event.listener.InventoryListener;
import nl.tabuu.tabuucore.hologram.Hologram;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIManager;
import nl.tabuu.tabuucore.metrics.bstats.Metrics;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.NMSVersion;
import nl.tabuu.tabuucore.plugin.TabuuCorePlugin;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Main class of TabuuCore.
 */
public class TabuuCore extends TabuuCorePlugin {

    private static TabuuCore _instance;
    private InventoryUIManager _inventoryUIManager;
    private CommandRegister _commandRegister;
    private Dictionary _local;

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
        getConfigurationManager().addConfiguration("config.yml", YamlConfiguration.class);
        _local = getConfigurationManager().addConfiguration("lang.yml", YamlConfiguration.class).getDictionary("");

        // Inventory user interface related.
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), getInstance());
        _inventoryUIManager = new InventoryUIManager();

        // Command register related.
        _commandRegister = new CommandRegister();

        // Hologram related.
        Bukkit.getPluginManager().registerEvents(new HologramListener(), getInstance());

        // Metrics.
        new Metrics(this);

        getInstance().getLogger().info("TabuuCore enabled.");
    }

    @Override
    public void onDisable(){
        getInstance().getLogger().info("Disabling TabuuCore...");

        // Closes all UIs managed by TabuuCore to prevent duplication of items.
        getInventoryUIManager().forEach(InventoryUI::closeAll);

        // Destroy all holograms.
        HologramAPI.getInstance().getHolograms().forEach(Hologram::destroy);

        getInstance().getLogger().info("TabuuCore disabled.");
    }

    /**
     * Returns the {@link CommandRegister} used by TabuuCore.
     * @return The {@link CommandRegister} used by TabuuCore.
     */
    public CommandRegister getCommandRegister() {
        return _commandRegister;
    }

    /**
     * Returns the {@link InventoryUIManager} used by TabuuCore.
     * @return The {@link InventoryUIManager} used by TabuuCore.
     */
    public InventoryUIManager getInventoryUIManager(){
        return _inventoryUIManager;
    }

    /**
     * Returns the instance of TabuuCore.
     * @return The instance of TabuuCore.
     */
    public static TabuuCore getInstance(){
        return _instance;
    }

    public Dictionary getLocal() {
        return _local;
    }
}
