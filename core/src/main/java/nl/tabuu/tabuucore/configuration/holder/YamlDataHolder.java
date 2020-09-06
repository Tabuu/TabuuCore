package nl.tabuu.tabuucore.configuration.holder;

import nl.tabuu.tabuucore.configuration.IDataHolder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class YamlDataHolder implements IDataHolder {

    protected Map<String, Object> _root;

    public YamlDataHolder() {
        _root = new HashMap<>();
    }

    public YamlDataHolder(Map<String, Object> root) {
        _root = root;
    }

    // region Yaml Utilities
    private Object getYamlTarget(String path, BiFunction<Map<String, Object>, String, Object> parentPropertyFunction) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if(parts.length < 1) return _root;
        else if(parts.length < 2) return parentPropertyFunction.apply(_root, parts[0]);

        String propertyName = parts[parts.length - 1];
        parts = Arrays.copyOfRange(parts, 0, parts.length - 1);

        Map<String, Object> parent = _root;

        for(String part : parts) {
            if(!parent.containsKey(part)) parent.put(part, new HashMap<String, Object>());
            else if(!(parent.get(part) instanceof Map)) return null;

            parent = (Map<String, Object>) parent.get(part);
        }

        return parentPropertyFunction.apply(parent, propertyName);
    }

    private void forYamlTarget(String path, BiConsumer<Map<String, Object>, String> parentPropertyConsumer) {
        getYamlTarget(path, (parent, property) -> {
            parentPropertyConsumer.accept(parent, property);
            return null;
        });
    }

    private Object getYamlElement(String path) {
        return getYamlTarget(path, Map::get);
    }

    private <T> T getYamlElementValue(String path, Function<Object, T> function) {
        try {
            return function.apply(getYamlElement(path));
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    private <T> List<T> getYamlArrayValues(String path, Function<Object, T> function) {
        Object element = getYamlElement(path);
        if(!(element instanceof List)) return null;

        List<?> array = (List<?>) element;
        List<T> list = new ArrayList<>();

        for(Object item : array) {
            try {
                if(item != null) list.add(function.apply(item));
            } catch (ClassCastException ignore) { }
        }

        return list;
    }

    // endregion

    @Override
    public void delete(String path) {
        if(path.isEmpty()) _root = new HashMap<>();
        else getYamlTarget(path, Map::remove);
    }

    @Override
    public IDataHolder createSection(String path) {
        Map<String, Object> child = new HashMap<>();
        forYamlTarget(path, (parent, property) -> parent.put(property, child));

        return new YamlDataHolder(child);
    }

    @Override
    public IDataHolder getDataSection(String path) {
        Object element = getYamlTarget(path, Map::get);
        if(!(element instanceof Map)) return null;
        else return new YamlDataHolder((Map<String, Object>) element);
    }

    @Override
    public void setDataSection(String path, IDataHolder data) {
        if(!(data instanceof YamlDataHolder))
            throw new IllegalArgumentException("DataSection must have the same type as parent.");

        YamlDataHolder section = (YamlDataHolder) data;
        forYamlTarget(path, (parent, property) -> parent.put(property, section._root));
    }

    private Set<String> getKeys(Set<String> keys, String path, Map<String, Object> object, boolean deep) {
        if(object == null) return keys;

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
        Object element;

        if(path.isEmpty()) element = _root;
        else element = getYamlElement(path);

        return getKeys(new HashSet<>(), path, element instanceof Map ? (Map<String, Object>) element : null, deep);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    @Override
    public String getString(String path) {
        return getYamlElementValue(path, String.class::cast);
    }

    @Override
    public List<String> getStringList(String path) {
        return getYamlArrayValues(path, String.class::cast);
    }

    @Override
    public Character getCharacter(String path) {
        return getYamlElementValue(path, Character.class::cast);
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return getYamlArrayValues(path, Character.class::cast);
    }

    @Override
    public Boolean getBoolean(String path) {
        return getYamlElementValue(path, Boolean.class::cast);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return getYamlArrayValues(path, Boolean.class::cast);
    }

    @Override
    public Byte getByte(String path) {
        return getYamlElementValue(path, Byte.class::cast);
    }

    @Override
    public List<Byte> getByteList(String path) {
        return getYamlArrayValues(path, Byte.class::cast);
    }

    @Override
    public Double getDouble(String path) {
        return getYamlElementValue(path, Double.class::cast);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return getYamlArrayValues(path, Double.class::cast);
    }

    @Override
    public Float getFloat(String path) {
        return getYamlElementValue(path, Float.class::cast);
    }

    @Override
    public List<Float> getFloatList(String path) {
        return getYamlArrayValues(path, Float.class::cast);
    }

    @Override
    public Integer getInteger(String path) {
        return getYamlElementValue(path, Integer.class::cast);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return getYamlArrayValues(path, Integer.class::cast);
    }

    @Override
    public Long getLong(String path) {
        return getYamlElementValue(path, Long.class::cast);
    }

    @Override
    public List<Long> getLongList(String path) {
        return getYamlArrayValues(path, Long.class::cast);
    }

    @Override
    public Short getShort(String path) {
        return getYamlElementValue(path, Short.class::cast);
    }

    @Override
    public List<Short> getShortList(String path) {
        return getYamlArrayValues(path, Short.class::cast);
    }

    @Override
    public void set(String path, String value) {
        forYamlTarget(path, (parent, property) -> parent.put(property, value));
    }

    @Override
    public void setStringList(String path, List<String> list) {
        forYamlTarget(path, (parent, property) -> parent.put(property, list));
    }

    @Override
    public void set(String path, char value) {
        forYamlTarget(path, (parent, property) -> parent.put(property, value));
    }

    @Override
    public void setCharacterList(String path, List<Character> list) {
        forYamlTarget(path, (parent, property) -> parent.put(property, list));
    }

    @Override
    public void set(String path, boolean value) {
        forYamlTarget(path, (parent, property) -> parent.put(property, value));
    }

    @Override
    public void setBooleanList(String path, List<Boolean> list) {
        forYamlTarget(path, (parent, property) -> parent.put(property, list));
    }

    @Override
    public void set(String path, Number value) {
        forYamlTarget(path, (parent, property) -> parent.put(property, value));
    }

    @Override
    public void setNumberList(String path, List<Number> list) {
        forYamlTarget(path, (parent, property) -> parent.put(property, list));
    }

}
