package nl.tabuu.tabuucore.plugin;

import nl.tabuu.tabuucore.configuration.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class TabuuCorePlugin extends JavaPlugin {

    private ConfigurationManager _configurationManager;

    public ConfigurationManager getConfigurationManager(){
        if(_configurationManager == null)
            _configurationManager = new ConfigurationManager(this);
        return _configurationManager;
    }
}
