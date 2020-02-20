package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public interface IConfiguration extends Configuration, ConfigurationSection {

    /**
     * Saves the configuration to file.
     */
    void save();

    /**
     * Deletes a specified path.
     * @param path The path to be deleted.
     */
    void delete(String path);

    /**
     * Reloads the configuration from file.
     */
    void reload();

    /**
     * Returns a {@link Location} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     * @param path The path to get the {@link Location} from.
     * @return A {@link Location} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     */
    @Override
    default Location getLocation(String path){
        return Serializer.LOCATION.deserialize(getString(path));
    }

    /**
     * Sets a {@link Location} to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     * @param path The path to set the {@link Location} to.
     * @param value The {@link Location} to set.
     */
    default void set(String path, Location value){
        set(path, Serializer.LOCATION.serialize(value));
    }

    /**
     * Returns a {@link Location} list from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     * @param path The path to get the {@link Location} list from.
     * @return A {@link Location} list from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     */
    default List<Location> getLocationList(String path){
        return getStringList(path).stream().map(s -> Serializer.LOCATION.deserialize(s)).collect(Collectors.toList());
    }

    /**
     * Sets a {@link Location} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     * @param path The path to set the {@link Location} list to.
     * @param value The {@link Location} list to set.
     */
    default void setLocationList(String path, List<Location> value){
        set(path, value.stream().map(l -> Serializer.LOCATION.serialize(l)).collect(Collectors.toList()));
    }

    /**
     * Returns an {@link OfflinePlayer} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     * @param path The path to get the {@link OfflinePlayer} from.
     * @return An {@link OfflinePlayer} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     */
    default OfflinePlayer getOfflinePlayer(String path){
        return Serializer.OFFLINE_PLAYER.deserialize(getString(path));
    }

    /**
     * Sets a {@link OfflinePlayer} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     * @param path The path to set the {@link OfflinePlayer} list to.
     * @param value The {@link OfflinePlayer} list to set.
     */
    default void set(String path, OfflinePlayer value){
        set(path, Serializer.OFFLINE_PLAYER.serialize(value));
    }

    /**
     * Returns an {@link Player} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     * @param path The path to get the {@link Player} from.
     * @return An {@link Player} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     */
    default Player getPlayer(String path){
        return Serializer.PLAYER.deserialize(getString(path));
    }

    /**
     * Sets a {@link Player} list to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     * @param path The path to set the {@link Player} list to.
     * @param value The {@link Player} list to set.
     */
    default void set(String path, Player value){
        set(path, Serializer.PLAYER.serialize(value));
    }

    /**
     * @deprecated Deprecated in favor of {@link #getEnum(Class materialClass, String path)}
     */
    @Deprecated
    default Material getMaterial(String path){
        return getEnum(Material.class, path);
    }

    /**
     * Returns an enum from the specified path.
     * @param enumClass The enum's class.
     * @param path The path to get the enum from.
     * @param <T> The enum's class.
     * @return An enum from the specified path.
     */
    default <T extends Enum<T>> T getEnum(Class<T> enumClass, String path){
        return Enum.valueOf(enumClass, getString(path));
    }

    /**
     * Sets an enum to the specified path.
     * @param path The path to set the enum to.
     * @param value The enum to set.
     */
    default void set(String path, Enum value){
        set(path, value.name());
    }

    /**
     * Returns an enum list from the specified path.
     * @param enumClass The enum's class.
     * @param path The path to get the enum list from.
     * @param <T> The enum's class.
     * @return An enum list from the specified path.
     */
    default <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path){
        return getStringList(path).stream()
                                    .map((string) -> Enum.valueOf(enumClass, string))
                                    .collect(Collectors.toList());
    }

    /**
     * Sets an enum list to the specified path.
     * @param path The path to set the enum list to.
     * @param values The enum list to set.
     */
    default <T extends Enum<T>> void setEnumList(String path, List<T> values){
        set(path, values.stream().map(Enum::name).collect(Collectors.toList()));
    }

    /**
     * Returns a {@link Long} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     * @param path The path to get the {@link Long} from.
     * @return A {@link Long} from the specified path. Deserialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     */
    default Long getTime(String path){
        return Serializer.TIME.deserialize(getString(path));
    }

    /**
     * Sets a {@link Long} to the specified path. Serialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     * @param path The path to set the {@link Long} to.
     * @param value The {@link Long} to set.
     */
    default void setTime(String path, Long value){
        set(path, Serializer.TIME.serialize(value));
    }

    /**
     * Returns a {@link Dictionary} from the specified path.
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
     * @param path The path to get the {@link Map} from.
     * @return A {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     */
    default Map<String, String> getMap(String path){
        return getMap(String.class, Serializer.STRING, path);
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     * @param valueClass The class to which to convert the value.
     * @param valueSerializer The serializer to deserialize the value from string.
     * @param path The path to get the {@link Map} from.
     * @param <V> The type to convert the value to.
     * @return The same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     */
    default <V> Map<String, V> getMap(Class<V> valueClass, AbstractStringSerializer<V> valueSerializer, String path){
        return getMap(String.class, valueClass, Serializer.STRING, valueSerializer, path);
    }

    /**
     * Returns the same as {@link #getMap(String path)} and {@link #getMap(Class valueClass, AbstractStringSerializer valueSerializer, String path)}, but converts the key with a (de)serializer.
     * @param keyClass The class to which to convert the key.
     * @param valueClass The class to which to convert the value.
     * @param keySerializer The serializer to deserialize the key from string.
     * @param valueSerializer The serializer to deserialize the value from string.
     * @param path The path to get the {@link Map} from.
     * @param <K> The type to convert the key to.
     * @param <V> The type to convert the value to.
     * @return The same as {@link #getMap(String path)} and {@link #getMap(Class valueClass, AbstractStringSerializer valueSerializer, String path)}, but converts the key with a (de)serializer.
     */
    default <K, V> Map<K, V> getMap(Class<K> keyClass, Class<V> valueClass, AbstractStringSerializer<K> keySerializer, AbstractStringSerializer<V> valueSerializer, String path){
        HashMap<K, V> map = new HashMap<>();
        ConfigurationSection section = getConfigurationSection(path);

        if(section == null) return map;
        Set<String> keys = section.getKeys(false);

        for(String key : keys){
            String value = getString(path + "." + key);
            map.put(keySerializer.deserialize(key), valueSerializer.deserialize(value));
        }
        return map;
    }

    default void setMap(String path, Map<String, String> values){
        setMap(String.class, Serializer.STRING, path, values);
    }

    default <V> void setMap(Class<V> valueClass, AbstractStringSerializer<V> valueSerializer, String path, Map<String, V> values){
        setMap(String.class, valueClass, Serializer.STRING, valueSerializer, path, values);
    }

    default <K, V> void setMap(Class<K> keyClass, Class<V> valueClass, AbstractStringSerializer<K> keySerializer, AbstractStringSerializer<V> valueSerializer, String path, Map<K, V> values){
        delete(path);

        for(Map.Entry<K, V> entry : values.entrySet()){
            String key = keySerializer.serialize(entry.getKey());
            String value = valueSerializer.serialize(entry.getValue());

            set(path + "." + key, value);
        }
    }
}
