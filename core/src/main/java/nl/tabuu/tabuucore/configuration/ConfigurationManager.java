package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

    private Plugin _plugin;
    private Map<String, IConfiguration> _configurations;

    public ConfigurationManager(Plugin plugin) {
        _plugin = plugin;
        _configurations = new HashMap<>();
    }

    /**
     * Creates, adds, and returns a configuration.
     * @param name The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @return The created configuration.
     */
    public IConfiguration addConfiguration(String name) {
        return addConfiguration(name, name + ".yml");
    }

    /**
     * Creates, adds, and returns a configuration.
     * @param name The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @param fileName The name of the internal file to be used.
     * @return The created configuration.
     */
    public IConfiguration addConfiguration(String name, String fileName) {
        return addConfiguration(name, fileName, fileName);
    }

    /**
     * Creates, adds, and returns a configuration.
     * @param name The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @param loadPath The path of the internal file to be used.
     * @param savePath The path to save the configuration to (relative to the plugin's data folder).
     * @return The created configuration.
     */
    public IConfiguration addConfiguration(String name, String loadPath, String savePath) {
        return addConfiguration(name, new YamlConfiguration(new File(_plugin.getDataFolder(), loadPath), _plugin.getResource(savePath)));
    }

    /**
     * Adds and returns a specified configuration.
     * @param name The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @param configuration The configuration to be added.
     * @return The specified configuration.
     */
    public IConfiguration addConfiguration(String name, IConfiguration configuration){
        _configurations.put(name, configuration);
        return getConfiguration(name);
    }

    /**
     * Returns the configuration with the specified name.
     * @param name The name of the configuration.
     * @return The configuration with the specified name.
     */
    public IConfiguration getConfiguration(String name) {
        return _configurations.get(name);
    }

    /**
     * Reloads all configurations.
     */
    public void reloadAll(){
        _configurations.values().forEach(IConfiguration::reload);
    }

}
