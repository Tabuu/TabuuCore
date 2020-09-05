package nl.tabuu.tabuucore.configuration.file;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.tabuu.tabuucore.configuration.IConfiguration;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.*;

public class JsonConfiguration implements IConfiguration {

    private JsonParser _parser;
    private JsonObject _root;
    private File _file;
    private InputStream _defaults;

    public JsonConfiguration(JsonObject object) {
        _root = object;
    }

    public JsonConfiguration(File file, InputStream defaults) {
        _parser = new JsonParser();
        _file = file;
        _defaults = defaults;
        writeDefaults();

        Reader jsonReader = null;

        try {
            jsonReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(jsonReader != null)
            _root = _parser.parse(jsonReader).getAsJsonObject();
    }

    private void writeDefaults() {
        if (!_file.exists())
            _file.getParentFile().mkdirs();

        if(_defaults == null) return;

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

    public String getJsonElementName(String path) {
        String[] parts = path.split("\\.");
        if(parts.length < 1) return "";
        else return parts[parts.length - 1];
    }

    public JsonElement getJsonElement(String path) {
        String[] parts = path.split("\\.");

        JsonObject current = _root;
        for(String part : parts) {
            if(!current.has(part)) return null;
            JsonElement element = current.get(part);

            if(!element.isJsonObject()) return element;
            current = element.getAsJsonObject();
        }

        return current;
    }

    public JsonObject getJsonParentObject(String path) {
        String[] parts = path.split("\\.");
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
    public void save() {
        try {
            Validate.notNull(_file, "File cannot be null");
            Files.createParentDirs(_file);
            String data = _root.toString();

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(_file), Charsets.UTF_8)) {
                writer.write(data);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(String path) {
        String[] parts = path.split("\\.");
        String elementName = parts[parts.length - 1];

    }

    @Override
    public void reload() {
        Reader jsonReader = null;

        try {
            jsonReader = new FileReader(_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(jsonReader != null)
            _root = _parser.parse(jsonReader).getAsJsonObject();
    }

    private Set<String> getKeys(Set<String> keys, String path, JsonObject object, boolean deep) {
        for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String name = entry.getKey();
            String newPath = path + (name.isEmpty() ? "" : "." + name);
            JsonElement element = entry.getValue();
            keys.add(newPath);

            if(deep && entry.getValue().isJsonObject())
                getKeys(keys, newPath, element.getAsJsonObject(), true);
        }

        return keys;
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys(new HashSet<>(), "", _root, deep);
    }

    @Override
    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> values = new HashMap<>();

        for(String key : getKeys(deep))
            values.put(key, getJsonElement(key).toString());

        return values;
    }

    @Override
    public boolean contains(String path) {
        return getJsonElement(path) != null;
    }

    @Override
    public boolean contains(String path, boolean ignoreDefault) {
        return contains(path);
    }

    @Override
    public boolean isSet(String path) {
        return contains(path);
    }

    @Override
    public String getCurrentPath() {
        throw new NotImplementedException();
    }

    @Override
    public String getName() {
        throw new NotImplementedException();
    }

    @Override
    public Configuration getRoot() {
        throw new NotImplementedException();
    }

    @Override
    public ConfigurationSection getParent() {
        throw new NotImplementedException();
    }

    @Override
    public Object get(String path) {
        return getJsonElement(path).toString();
    }

    @Override
    public Object get(String path, Object other) {
        return contains(path) ? getJsonElement(path).toString() : other;
    }

    @Override
    public void set(String path, Object object) {
        String name = getJsonElementName(path);

        if(object instanceof String)
            getJsonParentObject(path).addProperty(name, (String) object);

        else if (object instanceof Number)
            getJsonParentObject(path).addProperty(name, (Number) object);

        else if (object instanceof Boolean)
            getJsonParentObject(path).addProperty(name, (Boolean) object);

        else if (object instanceof Character)
            getJsonParentObject(path).addProperty(name, (Character) object);
    }

    @Override
    public ConfigurationSection createSection(String s) {
        throw new NotImplementedException();
    }

    @Override
    public ConfigurationSection createSection(String s, Map<?, ?> map) {
        throw new NotImplementedException();
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
    public String getString(String path, String other) {
        try {
            return getJsonElement(path).getAsString();
        } catch (ClassCastException | IllegalStateException exception) {
            return other;
        }
    }

    @Override
    public boolean isString(String path) {
        return getString(path) != null;
    }

    @Override
    public int getInt(String path) {
        try {
            return getJsonElement(path).getAsInt();
        } catch (ClassCastException | IllegalStateException exception) {
            return 0;
        }
    }

    @Override
    public int getInt(String path, int other) {
        try {
            return getJsonElement(path).getAsInt();
        } catch (ClassCastException | IllegalStateException exception) {
            return other;
        }
    }

    @Override
    public boolean isInt(String path) {
        try {
            getJsonElement(path).getAsInt();
            return true;
        } catch (ClassCastException | IllegalStateException exception) {
            return false;
        }
    }

    @Override
    public boolean getBoolean(String path) {
        try {
            return getJsonElement(path).getAsBoolean();
        } catch (ClassCastException | IllegalStateException exception) {
            return false;
        }
    }

    @Override
    public boolean getBoolean(String path, boolean other) {
        try {
            return getJsonElement(path).getAsBoolean();
        } catch (ClassCastException | IllegalStateException exception) {
            return other;
        }
    }

    @Override
    public boolean isBoolean(String path) {
        try {
            getJsonElement(path).getAsBoolean();
            return true;
        } catch (ClassCastException | IllegalStateException exception) {
            return false;
        }
    }

    @Override
    public double getDouble(String path) {
        try {
            return getJsonElement(path).getAsDouble();
        } catch (ClassCastException | IllegalStateException exception) {
            return 0d;
        }
    }

    @Override
    public double getDouble(String path, double other) {
        try {
            return getJsonElement(path).getAsDouble();
        } catch (ClassCastException | IllegalStateException exception) {
            return other;
        }
    }

    @Override
    public boolean isDouble(String path) {
        try {
            getJsonElement(path).getAsDouble();
            return true;
        } catch (ClassCastException | IllegalStateException exception) {
            return false;
        }
    }

    @Override
    public long getLong(String path) {
        try {
            return getJsonElement(path).getAsLong();
        } catch (ClassCastException | IllegalStateException exception) {
            return 0L;
        }
    }

    @Override
    public long getLong(String path, long other) {
        try {
            return getJsonElement(path).getAsLong();
        } catch (ClassCastException | IllegalStateException exception) {
            return other;
        }
    }

    @Override
    public boolean isLong(String path) {
        try {
            getJsonElement(path).getAsLong();
            return true;
        } catch (ClassCastException | IllegalStateException exception) {
            return false;
        }
    }

    @Override
    public List<?> getList(String path) {
        throw new NotImplementedException();
    }

    @Override
    public List<?> getList(String path, List<?> other) {
        // List<?> list = getList(path);
        // return list == null || list.isEmpty() ? other : list;
        return other;
    }

    @Override
    public boolean isList(String path) {
        JsonElement element = getJsonElement(path);
        return element != null && element.isJsonArray();
    }

    @Override
    public List<String> getStringList(String s) {
        return null;
    }

    @Override
    public List<Integer> getIntegerList(String s) {
        return null;
    }

    @Override
    public List<Boolean> getBooleanList(String s) {
        return null;
    }

    @Override
    public List<Double> getDoubleList(String s) {
        return null;
    }

    @Override
    public List<Float> getFloatList(String s) {
        return null;
    }

    @Override
    public List<Long> getLongList(String s) {
        return null;
    }

    @Override
    public List<Byte> getByteList(String s) {
        return null;
    }

    @Override
    public List<Character> getCharacterList(String s) {
        return null;
    }

    @Override
    public List<Short> getShortList(String s) {
        return null;
    }

    @Override
    public List<Map<?, ?>> getMapList(String s) {
        throw new NotImplementedException();
    }

    @Override
    public <T> T getObject(String s, Class<T> aClass) {
        throw new NotImplementedException();
    }

    @Override
    public <T> T getObject(String s, Class<T> aClass, T t) {
        throw new NotImplementedException();
    }

    @Override
    public <T extends ConfigurationSerializable> T getSerializable(String s, Class<T> aClass) {
        throw new NotImplementedException();
    }

    @Override
    public <T extends ConfigurationSerializable> T getSerializable(String s, Class<T> aClass, T t) {
        throw new NotImplementedException();
    }

    @Override
    public Vector getVector(String path) {
        return get(path, Serializer.VECTOR);
    }

    @Override
    public Vector getVector(String path, Vector other) {
        return getOrDefault(path, Serializer.VECTOR, other);
    }

    @Override
    public boolean isVector(String path) {
        return isDeserializable(path, Serializer.VECTOR);
    }

    @Override
    public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer offlinePlayer) {
        return getOrDefault(path, Serializer.OFFLINE_PLAYER, offlinePlayer);
    }

    @Override
    public boolean isOfflinePlayer(String path) {
        return isDeserializable(path, Serializer.OFFLINE_PLAYER);
    }

    @Override
    public ItemStack getItemStack(String path) {
        return get(path, Serializer.ITEMSTACK);
    }

    @Override
    public ItemStack getItemStack(String path, ItemStack itemStack) {
        return getOrDefault(path, Serializer.ITEMSTACK, itemStack);
    }

    @Override
    public boolean isItemStack(String path) {
        return isDeserializable(path, Serializer.ITEMSTACK);
    }

    @Override
    public Color getColor(String s) {
        throw new NotImplementedException();
    }

    @Override
    public Color getColor(String s, Color color) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isColor(String s) {
        throw new NotImplementedException();
    }

    @Override
    public Location getLocation(String path, Location location) {
        return getOrDefault(path, Serializer.LOCATION, location);
    }

    @Override
    public boolean isLocation(String path) {
        return isDeserializable(path, Serializer.LOCATION);
    }

    @Override
    public ConfigurationSection getConfigurationSection(String path) {
        return new JsonConfiguration(getJsonElement(path).getAsJsonObject());
    }

    @Override
    public boolean isConfigurationSection(String path) {
        return getJsonElement(path).isJsonObject();
    }

    @Override
    public ConfigurationSection getDefaultSection() {
        throw new NotImplementedException();
    }

    @Override
    public void addDefault(String s, Object o) {
        throw new NotImplementedException();
    }

    @Override
    public void addDefaults(Map<String, Object> map) {
        throw new NotImplementedException();
    }

    @Override
    public void addDefaults(Configuration configuration) {
        throw new NotImplementedException();
    }

    @Override
    public void setDefaults(Configuration configuration) {
        throw new NotImplementedException();
    }

    @Override
    public Configuration getDefaults() {
        throw new NotImplementedException();
    }

    @Override
    public ConfigurationOptions options() {
        throw new NotImplementedException();
    }
}
