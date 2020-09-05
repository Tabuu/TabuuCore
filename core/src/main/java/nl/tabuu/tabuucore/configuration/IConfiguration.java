package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface IConfiguration extends Configuration, ConfigurationSection {

    /**
     * Saves the configuration to file.
     */
    void save();

    /**
     * Deletes a specified path.
     *
     * @param path The path to be deleted.
     */
    void delete(String path);

    /**
     * Reloads the configuration from file.
     */
    void reload();

    /**
     * Gets a value from a path, using a deserializer. If the value is null, return the default value.
     * @param path          Path to get the value from.
     * @param deserializer  Deserializer to deserialize the value with.
     * @param def           The default value to return if null.
     * @param <V>           Type of the value.
     * @return              Value at the given path, or the default value if null.
     */
    default <V> V getOrDefault(String path, IObjectDeserializer<String, V> deserializer, V def) {
        V value = get(path, deserializer);
        return value == null ? def : value;
    }

    /**
     * Gets a value from a path, using a deserializer.
     *
     * @param path         Path to get the value from.
     * @param deserializer Deserializer to deserialize the value with.
     * @param <V>          Type of the value.
     * @return Value at the given path.
     */
    default <V> V get(String path, IObjectDeserializer<String, V> deserializer) {
        String value = getString(path);
        return value == null ? null : deserializer.deserialize(value);
    }

    /**
     * Sets a value to a specified path, using a serializer.
     *
     * @param path       Path to set the value to.
     * @param value      Value to be set to the path.
     * @param serializer Serializer to be used to serialize the value.
     * @param <V>        Type of the value.
     */
    default <V> void set(String path, V value, IObjectSerializer<V, String> serializer) {
        set(path, serializer.serialize(value));
    }

    default <V> boolean isDeserializable(String path, IObjectDeserializer<String, V> deserializer) {
        String value = getString(path);
        return deserializer.deserialize(value) != null;
    }

    /**
     * Gets a list from a path, using a deserializer.
     *
     * @param path         Path to get the list from.
     * @param deserializer Deserializer to deserialize the list items with.
     * @param <V>          Type of the list items.
     * @return List at the given path.
     */
    default <V> List<V> getList(String path, IObjectDeserializer<String, V> deserializer) {
        List<String> stringList = getStringList(path);
        return stringList.stream().map(deserializer::deserialize).collect(Collectors.toList());
    }

    /**
     * Sets a list to a specified path, using a serializer.
     *
     * @param path       Path to set the list to.
     * @param list       List to be set to the path.
     * @param serializer Serializer to be used to serialize the list items.
     * @param <V>        Type of the list items.
     */
    default <V> void setList(String path, List<V> list, IObjectSerializer<V, String> serializer) {
        List<String> stringList = list.stream().map(serializer::serialize).collect(Collectors.toList());
        set(path, stringList);
    }

    /**
     * Sets a {@link Location} to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     *
     * @param path  The path to set the {@link Location} to.
     * @param value The {@link Location} to set.
     */
    default void set(String path, Location value) {
        set(path, Serializer.LOCATION.serialize(value));
    }

    /**
     * Returns a {@link Location} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     *
     * @param path The path to get the {@link Location} from.
     * @return A {@link Location} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     */
    @Override
    default Location getLocation(String path) {
        return get(path, Serializer.LOCATION);
    }

    /**
     * Returns an enum list from the specified path.
     *
     * @param enumClass The enum's class.
     * @param path      The path to get the enum list from.
     * @param <T>       The enum's class.
     * @return An enum list from the specified path.
     */
    default <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path) {
        return getList(path, (string) -> Enum.valueOf(enumClass, string));
    }

    /**
     * Sets an enum list to the specified path.
     *
     * @param path   The path to set the enum list to.
     * @param values The enum list to set.
     */
    default <T extends Enum<T>> void setEnumList(String path, List<T> values) {
        setList(path, values, Enum::name);
    }

    /**
     * Returns a {@link Dictionary} from the specified path.
     *
     * @param path The path to get the {@link Dictionary} from.
     * @return A {@link Dictionary} from the specified path.
     */
    default Dictionary getDictionary(String path) {
        Dictionary dictionary = new Dictionary();
        getMap(path).forEach(dictionary::put);

        return dictionary;
    }

    /**
     * Returns a {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     *
     * @param path The path to get the {@link Map} from.
     * @return A {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     */
    default Map<String, String> getMap(String path) {
        return getMap(path, Serializer.STRING);
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     *
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String map)}, but converts the value with a deserializer.
     */
    default <V> Map<String, V> getMap(String path, IObjectDeserializer<String, V> valueDeserializer) {
        return getMap(path, Serializer.STRING, valueDeserializer);
    }

    /**
     * Returns the same as {@link #getMap(String path)} and {@link #getMap(String, IObjectDeserializer)}, but converts the key with a deserializer.
     *
     * @param keyDeserializer   The deserializer to deserialize the key from string.
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <K>               The type to convert the key to.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String path)} and {@link #getMap(Class valueClass, AbstractStringSerializer valueSerializer, String path)}, but converts the key with a (de)serializer.
     */
    default <K, V> Map<K, V> getMap(String path, IObjectDeserializer<String, K> keyDeserializer, IObjectDeserializer<String, V> valueDeserializer) {
        HashMap<K, V> map = new HashMap<>();
        ConfigurationSection section = getConfigurationSection(path);

        if (section == null) return map;
        Set<String> keys = section.getKeys(false);

        for (String key : keys) {
            String value = getString(path + "." + key);
            map.put(keyDeserializer.deserialize(key), valueDeserializer.deserialize(value));
        }
        return map;
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path Path to set the map to.
     */
    default void setMap(String path, Map<String, String> values) {
        setMap(path, values, Serializer.STRING);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <V>             Type of the value.
     */
    default <V> void setMap(String path, Map<String, V> map, IObjectSerializer<V, String> valueSerializer) {
        setMap(path, map, Serializer.STRING, valueSerializer);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param keySerializer   Serializer used to serialize the key.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <K>             Type of the key.
     * @param <V>             Type of the value.
     */
    default <K, V> void setMap(String path, Map<K, V> map, IObjectSerializer<K, String> keySerializer, IObjectSerializer<V, String> valueSerializer) {
        if (path.isEmpty())
            throw new IllegalArgumentException("Cannot set to an empty path!");

        delete(path);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            String key = keySerializer.serialize(entry.getKey());
            String value = valueSerializer.serialize(entry.getValue());
            set(path + "." + key, value);
        }
    }

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
