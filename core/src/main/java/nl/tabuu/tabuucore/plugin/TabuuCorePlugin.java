package nl.tabuu.tabuucore.plugin;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.command.register.ICommandListener;
import nl.tabuu.tabuucore.configuration.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a TabuuCorePlugin
 */
public abstract class TabuuCorePlugin extends JavaPlugin {

    private ConfigurationManager _configurationManager;

    /**
     * Returns the {@link ConfigurationManager} used by this plugin.
     * @return the {@link ConfigurationManager} used by this plugin.
     */
    public ConfigurationManager getConfigurationManager(){
        if(_configurationManager == null)
            _configurationManager = new ConfigurationManager(this);
        return _configurationManager;
    }

    /**
     * Registers the command executors from a class.
     * @param object A class with command executors.
     * @see nl.tabuu.tabuucore.command.register.annotation.CommandExecutor
     */
    public void registerExecutors(ICommandListener object) {
        TabuuCore.getInstance().getCommandRegister().registerExecutors(object, this);
    }
}
