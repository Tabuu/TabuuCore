package nl.tabuu.tabuucore.configuration.file;

import nl.tabuu.tabuucore.configuration.IConfiguration;
import nl.tabuu.tabuucore.configuration.holder.YamlDataHolder;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.NMSVersion;
import org.bukkit.configuration.file.YamlConstructor;
import org.yaml.snakeyaml.DumperOptions;
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
        Representer representer = new Representer();

        dumper.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        if (NMSUtil.getVersion().isPost(NMSVersion.v1_8_R3)) {
            org.yaml.snakeyaml.LoaderOptions loader = new org.yaml.snakeyaml.LoaderOptions();
            _parser = new Yaml(new YamlConstructor(), representer, dumper, loader);
        } else _parser = new Yaml(new YamlConstructor(), representer, dumper);

        writeDefaults();
    }

    @Override
    public InputStream getDefaults() {
        return _defaults;
    }

    @Override
    public void reload() {
        try (Reader yamlReader = new FileReader(getFile())) {
            Object object = _parser.load(yamlReader);
            if (object != null) setRoot((Map<String, Object>) object); // Cast for 1.8
            else setRoot(createEmptyParent());
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
        return _parser.dump(getRoot());
    }
}