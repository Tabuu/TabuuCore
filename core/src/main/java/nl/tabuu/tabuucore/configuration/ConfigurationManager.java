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

    public void addConfiguration(String name) {
        addConfiguration(name, name + ".yml");
    }

    public void addConfiguration(String name, String fileName) {
        addConfiguration(name, fileName, fileName);
    }

    public void addConfiguration(String name, String loadPath, String savePath) {
        addConfiguration(name, new YamlConfiguration(new File(_plugin.getDataFolder(), loadPath), _plugin.getResource(savePath)));
    }

    public void addConfiguration(String name, IConfiguration configuration){
        _configurations.put(name, configuration);
    }

    public IConfiguration getConfiguration(String name) {
        return _configurations.get(name);
    }

    public void reloadAll(){
        _configurations.values().forEach(IConfiguration::reload);
    }

}
