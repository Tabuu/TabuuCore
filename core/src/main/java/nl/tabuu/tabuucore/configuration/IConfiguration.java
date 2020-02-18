package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;
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
    default void setEnumList(String path, List<Enum> values){
        values.forEach(e -> set(path, e)); //BUG: Does this even work?!?
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
        ConfigurationSection configurationSection = getConfigurationSection(path);

        for (String key : configurationSection.getKeys(false))
            dictionary.put(key, getString(path + "." + key));

        return dictionary;
    }
}
