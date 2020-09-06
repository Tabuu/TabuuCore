package nl.tabuu.tabuucore.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;
import java.util.Map;

public interface IConfiguration extends IDataHolder {

    /**
     * Returns the file to which this configuration is linked.
     * @return The file to which this configuration is linked.
     */
    File getFile();

    InputStream getDefaults();

    default void writeDefaults() {
        if (!getFile().exists())
            getFile().getParentFile().mkdirs();

        try {
            if (!getFile().exists()) {
                if(getDefaults() != null) {
                    InputStream in = getDefaults();
                    OutputStream out = new FileOutputStream(getFile());
                    byte[] buf = new byte[in.available()];
                    int len;
                    while ((len = in.read(buf)) > 0)
                        out.write(buf, 0, len);
                    in.close();
                    out.close();
                } else getFile().createNewFile();
            }
            reload();
        } catch (IOException ex) {
            Bukkit.getLogger().severe("Plugin unable to write configuration file " + getFile().getName() + "!");
        }
    }

    /**
     * Saves the configuration to file.
     */
    @SuppressWarnings("all")
    default void save() {
        try {
            Files.createParentDirs(getFile());
            String data = toString();

            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(getFile()), Charsets.UTF_8)) {
                writer.write(data);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Reloads the configuration from file.
     */
    void reload();

    // region Deprecated
    /**
     * Returns a {@link Location} list from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     *
     * @param path The path to get the {@link Location} list from.
     * @return A {@link Location} list from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     * @deprecated Deprecated in favor of {@link #getList(String, IObjectDeserializer)}
     */
    @Deprecated /*6th of april 2020*/
    default List<Location> getLocationList(String path) {
        return getList(path, Serializer.LOCATION);
    }

    /**
     * Sets a {@link Location} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     *
     * @param path  The path to set the {@link Location} list to.
     * @param value The {@link Location} list to set.
     * @deprecated Deprecated in favor of {@link #setList(String, List, IObjectSerializer)}
     */
    @Deprecated /*6th of april 2020*/
    default void setLocationList(String path, List<Location> value) {
        setList(path, value, Serializer.LOCATION);
    }

    /**
     * Returns an {@link OfflinePlayer} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     *
     * @param path The path to get the {@link OfflinePlayer} from.
     * @return An {@link OfflinePlayer} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     * @deprecated Deprecated in favor of {@link #get(String, IObjectDeserializer)}
     */
    @Deprecated /*6th of april 2020*/
    default OfflinePlayer getOfflinePlayer(String path) {
        return get(path, Serializer.OFFLINE_PLAYER);
    }

    /**
     * Sets a {@link OfflinePlayer} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     *
     * @param path  The path to set the {@link OfflinePlayer} list to.
     * @param value The {@link OfflinePlayer} list to set.
     * @deprecated Deprecated in favor of {@link #set(String, Object, IObjectSerializer)}
     */
    @Deprecated /*6th of april 2020*/
    default void set(String path, OfflinePlayer value) {
        set(path, value, Serializer.OFFLINE_PLAYER);
    }

    /**
     * Returns an {@link Player} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     *
     * @param path The path to get the {@link Player} from.
     * @return An {@link Player} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     * @deprecated Deprecated in favor of {@link #get(String, IObjectDeserializer)}
     */
    @Deprecated /*6th of april 2020*/
    default Player getPlayer(String path) {
        return get(path, Serializer.PLAYER);
    }

    /**
     * Returns an enum from the specified path.
     *
     * @param enumClass The enum's class.
     * @param path      The path to get the enum from.
     * @param <T>       The enum's class.
     * @return An enum from the specified path.
     * @deprecated Deprecated in favor of {@link Enum#valueOf}
     */
    @Deprecated
    default <T extends Enum<T>> T getEnum(Class<T> enumClass, String path) {
        return get(path, (string) -> Enum.valueOf(enumClass, string));
    }

    /**
     * Sets an enum to the specified path.
     *
     * @param path  The path to set the enum to.
     * @param value The enum to set.
     * @deprecated Deprecated in favor of {@link Enum#name()}
     */
    @Deprecated
    default void set(String path, Enum value) {
        set(path, value.name());
    }

    /**
     * Sets a {@link Player} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     *
     * @param path  The path to set the {@link Player} list to.
     * @param value The {@link Player} list to set.
     * @deprecated Deprecated in favor of {@link #set(String, Object, IObjectSerializer)}
     */
    @Deprecated /*6th of april 2020*/
    default void set(String path, Player value) {
        set(path, value, Serializer.PLAYER);
    }

    /**
     * @deprecated Deprecated in favor of {@link #getEnum(Class materialClass, String path)}
     */
    @Deprecated /*6th of april 2020*/
    default Material getMaterial(String path) {
        return getEnum(Material.class, path);
    }

    /**
     * Returns a {@link Long} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     *
     * @param path The path to get the {@link Long} from.
     * @return A {@link Long} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     * @deprecated Deprecated in favor of {@link #get(String, IObjectDeserializer)}
     */
    @Deprecated /*6th of april 2020*/
    default Long getTime(String path) {
        return get(path, Serializer.TIME);
    }

    /**
     * Sets a {@link Long} to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     *
     * @param path  The path to set the {@link Long} to.
     * @param value The {@link Long} to set.
     * @deprecated Deprecated in favor of {@link #set(String, Object, IObjectSerializer)}
     */
    @Deprecated /*6th of april 2020*/
    default void setTime(String path, Long value) {
        set(path, Serializer.TIME.serialize(value));
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     *
     * @param valueClass      The class to which to convert the value.
     * @param valueSerializer The serializer to deserialize the value from string.
     * @param path            The path to get the {@link Map} from.
     * @param <V>             The type to convert the value to.
     * @return The same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     * @deprecated Deprecated in favor of {@link #getMap(String, IObjectDeserializer)}
     */
    @Deprecated /*6th of april 2020*/
    default <V> Map<String, V> getMap(Class<V> valueClass, AbstractStringSerializer<V> valueSerializer, String path) {
        return getMap(String.class, valueClass, Serializer.STRING, valueSerializer, path);
    }

    /**
     * Returns the same as {@link #getMap(String path)} and {@link #getMap(Class valueClass, AbstractStringSerializer valueSerializer, String path)}, but converts the key with a (de)serializer.
     *
     * @param keyClass        The class to which to convert the key.
     * @param valueClass      The class to which to convert the value.
     * @param keySerializer   The serializer to deserialize the key from string.
     * @param valueSerializer The serializer to deserialize the value from string.
     * @param path            The path to get the {@link Map} from.
     * @param <K>             The type to convert the key to.
     * @param <V>             The type to convert the value to.
     * @return The same as {@link #getMap(String path)} and {@link #getMap(Class valueClass, AbstractStringSerializer valueSerializer, String path)}, but converts the key with a (de)serializer.
     * @deprecated Deprecated in favor of {@link #getMap(String, IObjectDeserializer, IObjectDeserializer)}.
     */
    @Deprecated /*6th of april 2020*/
    default <K, V> Map<K, V> getMap(Class<K> keyClass, Class<V> valueClass, AbstractStringSerializer<K> keySerializer, AbstractStringSerializer<V> valueSerializer, String path) {
        return getMap(path, keySerializer, valueSerializer);
    }

    /**
     * @deprecated Deprecated in favor of {@link #setMap(String, Map, IObjectSerializer)}
     */
    @Deprecated
    default <V> void setMap(Class<V> valueClass, AbstractStringSerializer<V> valueSerializer, String path, Map<String, V> values) {
        setMap(String.class, valueClass, Serializer.STRING, valueSerializer, path, values);
    }

    /**
     * @deprecated Deprecated in favor of {@link #setMap(String, Map, IObjectSerializer, IObjectSerializer)}
     */
    @Deprecated
    default <K, V> void setMap(Class<K> keyClass, Class<V> valueClass, AbstractStringSerializer<K> keySerializer, AbstractStringSerializer<V> valueSerializer, String path, Map<K, V> values) {
        setMap(path, values, keySerializer, valueSerializer);
    }
    // endregion
}
