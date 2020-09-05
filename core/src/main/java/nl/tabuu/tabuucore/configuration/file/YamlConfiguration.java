package nl.tabuu.tabuucore.configuration.file;

import nl.tabuu.tabuucore.configuration.IConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.util.*;

public class YamlConfiguration implements IConfiguration {

    private File _file;
    private InputStream _defaults;
    private Map<String, Object> _root;

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

    public String getYamlElementName(String path) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if(parts.length < 1) return "";
        else return parts[parts.length - 1];
    }

    public Object getYamlElement(String path) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        Map<String, Object> current = _root;
        for(String part : parts) {
            if(!current.containsKey(part)) return null;
            Object element = current.get(part);

            if(!(element instanceof Map)) return element;
            current = (Map<String, Object>) element;
        }

        return current;
    }

    public Map<String, Object> getYamlParentObject(String path) {
        String[] parts = path.split("\\.");
        if(parts.length < 2) return _root;

        parts = Arrays.copyOfRange(parts, 0, parts.length - 1);

        Map<String, Object> current = _root;
        for(String part : parts) {
            if(!current.containsKey(part)) current.put(part, new HashMap<String, Object>());
            else if(!(current.get(part) instanceof Map)) return null;

            current = (Map<String, Object>) current.get(part);
        }

        return current;
    }

    @Override
    public void delete(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.remove(name);
    }

    private Set<String> getKeys(Set<String> keys, String path, Map<String, Object> object, boolean deep) {
        for(Map.Entry<String, Object> entry : object.entrySet()) {
            String name = entry.getKey();
            String newPath = path + (path.isEmpty() ? "" : ".") + name;
            Object element = entry.getValue();
            keys.add(newPath);

            if(deep && element instanceof Map)
                getKeys(keys, newPath, (Map<String, Object>) element, true);
        }

        return keys;
    }

    @Override
    public Set<String> getKeys(String path, boolean deep) {
        return getKeys(new HashSet<>(), path, path.isEmpty() ? _root : (Map) getYamlElement(path), deep);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    @Override
    public String getString(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof String ? (String) value : null;
    }

    @Override
    public List<String> getStringList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof String ? (List<String>) list : null;
    }

    @Override
    public Character getCharacter(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Character ? (Character) value : null;
    }

    @Override
    public List<Character> getCharacterList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Character ? (List<Character>) list : null;
    }

    @Override
    public Boolean getBoolean(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Boolean ? (Boolean) value : null;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Boolean ? (List<Boolean>) list : null;
    }

    @Override
    public Byte getByte(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Byte ? (Byte) value : null;
    }

    @Override
    public List<Byte> getByteList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Byte ? (List<Byte>) list : null;
    }

    @Override
    public Double getDouble(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Double ? (Double) value : null;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Double ? (List<Double>) list : null;
    }

    @Override
    public Float getFloat(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Float ? (Float) value : null;
    }

    @Override
    public List<Float> getFloatList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Float ? (List<Float>) list : null;
    }

    @Override
    public Integer getInteger(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Integer ? (Integer) value : null;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Integer ? (List<Integer>) list : null;
    }

    @Override
    public Long getLong(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Long ? (Long) value : null;
    }

    @Override
    public List<Long> getLongList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Long ? (List<Long>) list : null;
    }

    @Override
    public Short getShort(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        return value instanceof Short ? (Short) value : null;
    }

    @Override
    public List<Short> getShortList(String path) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        Object value = object.get(name);
        if(!(value instanceof List)) return null;

        List<?> list = (List<?>) value;
        if(list.size() <= 0) return Collections.emptyList();

        Object sample = list.get(0);
        return sample instanceof Short ? (List<Short>) list : null;
    }

    @Override
    public void set(String path, String value) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, value);
    }

    @Override
    public void setStringList(String path, List<String> list) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, list);
    }

    @Override
    public void set(String path, char value) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, value);
    }

    @Override
    public void setCharacterList(String path, List<Character> list) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, list);
    }

    @Override
    public void set(String path, boolean value) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, value);
    }

    @Override
    public void setBooleanList(String path, List<Boolean> list) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, list);
    }

    @Override
    public void set(String path, Number value) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, value);
    }

    @Override
    public void setNumberList(String path, List<Number> list) {
        String name = getYamlElementName(path);
        Map<String, Object> object = getYamlParentObject(path);

        object.put(name, list);
    }

    @Override
    public String toString() {
        return _parser.dump(_root);
    }
}
