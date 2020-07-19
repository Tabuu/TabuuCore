package nl.tabuu.tabuucore.configuration.file;

import nl.tabuu.tabuucore.configuration.IConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.*;

public class YamlConfiguration extends org.bukkit.configuration.file.YamlConfiguration implements IConfiguration {

    File _file;
    InputStream _defaults;

    public YamlConfiguration(File file, InputStream defaults){
        _file = file;
        _defaults = defaults;

        this.writeDefaults();
    }

    @Override
    public void save() {
        try{
            save(_file);
        }
        catch(IOException exception){
            Bukkit.getLogger().severe("Could not save configuration file '" + _file.getName() + "'!");
        }
    }

    @Override
    public void delete(String path) {
        if(path.isEmpty())
            throw new IllegalArgumentException("Cannot set to an empty path!");
        super.set(path, null);
    }

    private void writeDefaults(){
        if (!_file.exists())
            _file.getParentFile().mkdirs();

        if (_defaults != null) {
            try {
                if (!_file.exists()) {
                    InputStream in = _defaults;
                    OutputStream out = new FileOutputStream(_file);
                    byte[] buf = new byte[_defaults.available()];
                    int len;
                    while ((len = in.read(buf)) > 0)
                        out.write(buf, 0, len);
                    out.close();
                    in.close();
                }
                reload();
            } catch (IOException ex) {
                Bukkit.getLogger().severe("Plugin unable to write configuration file " + _file.getName() + "!");
            }
        }
    }

    public void reload(){
        try {
            load(_file);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getLogger().severe("Could not load configuration file '" + _file.getName() + "'!");
        }
    }

    @Override
    public Location getLocation(String path) {
        return IConfiguration.super.getLocation(path);
    }
}
