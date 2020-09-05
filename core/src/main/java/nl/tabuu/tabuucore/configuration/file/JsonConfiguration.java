package nl.tabuu.tabuucore.configuration.file;

import com.google.gson.*;
import nl.tabuu.tabuucore.configuration.IConfiguration;

import java.io.*;
import java.util.*;

public class JsonConfiguration implements IConfiguration {

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonParser _parser;
    private JsonObject _root;
    private File _file;
    private InputStream _defaults;

    public JsonConfiguration(File file, InputStream defaults) {
        _file = file;
        _defaults = defaults;
        _parser = new JsonParser();
        writeDefaults();
    }

    @Override
    public InputStream getDefaults() {
        return _defaults;
    }

    @Override
    public File getFile() {
        return _file;
    }

    @Override
    public void reload() {
        try (Reader jsonReader = new FileReader(getFile())){
            JsonElement element = _parser.parse(jsonReader);
            if(element.isJsonObject()) _root = element.getAsJsonObject();
            else _root = new JsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJsonElementName(String path) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if(parts.length < 1) return "";
        else return parts[parts.length - 1];
    }

    public JsonElement getJsonElement(String path) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        JsonObject current = _root;
        for(String part : parts) {
            if(!current.has(part)) return null;
            JsonElement element = current.get(part);

            if(!element.isJsonObject()) return element;
            current = element.getAsJsonObject();
        }

        return current;
    }

    public JsonArray getJsonArray(String path) {
        JsonElement element = getJsonElement(path);
        if(!element.isJsonArray()) return null;

        return element.getAsJsonArray();
    }

    public JsonObject getJsonParentObject(String path) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);
        if(parts.length < 2) return _root;

        parts = Arrays.copyOfRange(parts, 0, parts.length - 1);

        JsonObject current = _root;
        for(String part : parts) {
            if(!current.has(part)) current.add(part, new JsonObject());
            else if(!current.get(part).isJsonObject()) return null;

            current = current.getAsJsonObject(part);
        }

        return current;
    }

    @Override
    public void delete(String path) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        object.remove(name);
    }

    private Set<String> getKeys(Set<String> keys, String path, JsonObject object, boolean deep) {
        for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String name = entry.getKey();
            String newPath = path + (name.isEmpty() ? "" : "." + name);
            JsonElement element = entry.getValue();
            keys.add(newPath);

            if(deep && element.isJsonObject())
                getKeys(keys, newPath, element.getAsJsonObject(), true);
        }

        return keys;
    }

    @Override
    public Set<String> getKeys(String path, boolean deep) {
        return getKeys(new HashSet<>(), path, _root, deep);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    @Override
    public String getString(String path) {
        try {
            return getJsonElement(path).getAsString();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<String> getStringList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<String> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsString());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public void set(String path, String value) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        object.addProperty(name, value);
    }

    @Override
    public void setStringList(String path, List<String> list) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        JsonArray array = new JsonArray();
        list.forEach(array::add);

        object.add(name, array);
    }

    @Override
    public Boolean getBoolean(String path) {
        try {
            return getJsonElement(path).getAsBoolean();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Boolean> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsBoolean());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public void set(String path, boolean value) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        object.addProperty(name, value);
    }

    @Override
    public void setBooleanList(String path, List<Boolean> list) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        JsonArray array = new JsonArray();
        list.forEach(array::add);

        object.add(name, array);
    }

    @Override
    public Character getCharacter(String path) {
        try {
            return getJsonElement(path).getAsCharacter();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Character> getCharacterList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Character> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsCharacter());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public void set(String path, char value) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        object.addProperty(name, value);
    }

    @Override
    public void setCharacterList(String path, List<Character> list) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        JsonArray array = new JsonArray();
        list.forEach(array::add);

        object.add(name, array);
    }

    @Override
    public Double getDouble(String path) {
        try {
            return getJsonElement(path).getAsDouble();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Double> getDoubleList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Double> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsDouble());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public Long getLong(String path) {
        try {
            return getJsonElement(path).getAsLong();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Long> getLongList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Long> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsLong());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public Integer getInteger(String path) {
        try {
            return getJsonElement(path).getAsInt();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Integer> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsInt());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public Float getFloat(String path) {
        try {
            return getJsonElement(path).getAsFloat();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Float> getFloatList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Float> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsFloat());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public Byte getByte(String path) {
        try {
            return getJsonElement(path).getAsByte();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Byte> getByteList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Byte> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsByte());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public Short getShort(String path) {
        try {
            return getJsonElement(path).getAsShort();
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    @Override
    public List<Short> getShortList(String path) {
        JsonArray array = getJsonArray(path);
        if(array == null) return null;

        List<Short> list = new ArrayList<>();

        for(JsonElement element : array) {
            try {
                list.add(element.getAsShort());
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Override
    public void set(String path, Number value) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        object.addProperty(name, value);
    }

    @Override
    public void setNumberList(String path, List<Number> list) {
        String name = getJsonElementName(path);
        JsonObject object = getJsonParentObject(path);

        JsonArray array = new JsonArray();
        list.forEach(array::add);

        object.add(name, array);
    }

    @Override
    public String toString() {
        return GSON.toJson(_root);
    }
}