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

    public IConfiguration addConfiguration(String name) {
        return addConfiguration(name, name + ".yml");
    }

    public IConfiguration addConfiguration(String name, String fileName) {
        return addConfiguration(name, fileName, fileName);
    }

    public IConfiguration addConfiguration(String name, String loadPath, String savePath) {
        return addConfiguration(name, new YamlConfiguration(new File(_plugin.getDataFolder(), loadPath), _plugin.getResource(savePath)));
    }

    public IConfiguration addConfiguration(String name, IConfiguration configuration){
        _configurations.put(name, configuration);
        return getConfiguration(name);
    }

    public IConfiguration getConfiguration(String name) {
        return _configurations.get(name);
    }

    public void reloadAll(){
        _configurations.values().forEach(IConfiguration::reload);
    }

}
