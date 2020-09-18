package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
     *
     * @param name The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @return The created configuration.
     */
    @Deprecated
    public IConfiguration addConfiguration(String name) {
        return addConfiguration(name, name + ".yml", YamlConfiguration.class);
    }

    public <T extends IConfiguration> T addConfiguration(String filePath, Class<T> configType) {
        return addConfiguration(filePath, filePath, filePath, configType);
    }

    public <T extends IConfiguration> T addConfiguration(String name, String filePath, Class<T> configType) {
        return addConfiguration(name, filePath, filePath, configType);
    }

    public <T extends IConfiguration> T addConfiguration(String name, String filePath, String resourcePath, Class<T> configType) {
        File file = new File(_plugin.getDataFolder(), filePath);
        InputStream defaults = _plugin.getResource(resourcePath);

        try {
            Constructor<T> constructor = configType.getConstructor(File.class, InputStream.class);
            T configuration = constructor.newInstance(file, defaults);
            addConfiguration(name, configuration);
            return configuration;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            _plugin.getLogger().severe("Could not add configuration. Invalid type.");
            e.printStackTrace();
        } catch (IllegalStateException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * Creates, adds, and returns a configuration.
     *
     * @param name         The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @param resourcePath The path of the internal file to be used.
     * @param filePath     The path to save the configuration to (relative to the plugin's data folder).
     * @return The created configuration.
     */
    @Deprecated
    public IConfiguration addConfiguration(String name, String filePath, String resourcePath) {
        return addConfiguration(name, filePath, resourcePath, YamlConfiguration.class);
    }

    /**
     * Adds and returns a specified configuration.
     *
     * @param name          The name to be given to the configuration. This name is needed to fetch the configuration with the {@link #getConfiguration(String name)} method.
     * @param configuration The configuration to be added.
     * @return The specified configuration.
     */
    public IConfiguration addConfiguration(String name, IConfiguration configuration) {
        _configurations.put(name, configuration);
        return getConfiguration(name);
    }

    /**
     * Returns the configuration with the specified name.
     *
     * @param name The name of the configuration.
     * @return The configuration with the specified name.
     */
    public IConfiguration getConfiguration(String name) {
        return _configurations.get(name);
    }

    /**
     * Reloads all configurations.
     */
    public void reloadAll() {
        _configurations.values().forEach(IConfiguration::reload);
    }
}