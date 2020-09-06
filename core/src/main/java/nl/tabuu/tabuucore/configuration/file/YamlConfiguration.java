package nl.tabuu.tabuucore.configuration.file;

import nl.tabuu.tabuucore.configuration.IConfiguration;
import nl.tabuu.tabuucore.configuration.holder.YamlDataHolder;
import org.bukkit.configuration.file.YamlConstructor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

public class YamlConfiguration extends YamlDataHolder implements IConfiguration {

    private File _file;
    private InputStream _defaults;
    private final Yaml _parser;

    public YamlConfiguration(File file, InputStream defaults) {
        _file = file;
        _defaults = defaults;

        // Using the same style as the Bukkit YAML files.
        DumperOptions dumper = new DumperOptions();
        LoaderOptions loader = new LoaderOptions();
        Representer representer = new Representer();

        dumper.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        _parser = new Yaml(new YamlConstructor(), representer, dumper, loader);
        writeDefaults();
    }

    @Override
    public InputStream getDefaults() {
        return _defaults;
    }

    @Override
    public void reload() {
        try (Reader yamlReader = new FileReader(getFile())){
            Map<String, Object> element = _parser.load(yamlReader);
            if(element != null) _root = element;
            else _root = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getFile() {
        return _file;
    }

    @Override
    public String toString() {
        return _parser.dump(_root);
    }
}
