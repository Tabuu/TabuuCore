package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IConfiguration extends Configuration {

    void save();
    void delete(String path);
    void reload();

    default Location getLocation(String path){
        return Serializer.LOCATION.deserialize(getString(path));
    }
    default void set(String path, Location value){
        set(path, Serializer.LOCATION.serialize(value));
    }

    default List<Location> getLocationList(String path){
        return getStringList(path).stream().map(s -> Serializer.LOCATION.deserialize(s)).collect(Collectors.toList());
    }
    default void setLocationList(String path, List<Location> value){
        set(path, value.stream().map(l -> Serializer.LOCATION.serialize(l)).collect(Collectors.toList()));
    }

    default OfflinePlayer getOfflinePlayer(String path){
        return Serializer.OFFLINE_PLAYER.deserialize(getString(path));
    }
    default void set(String path, OfflinePlayer value){
        set(path, Serializer.OFFLINE_PLAYER.serialize(value));
    }

    default Player getPlayer(String path){
        return Serializer.PLAYER.deserialize(getString(path));
    }
    default void set(String path, Player value){
        set(path, Serializer.PLAYER.serialize(value));
    }

    @Deprecated
    default Material getMaterial(String path){
        return Material.valueOf(getString(path));
    }

    default <T extends Enum<T>> T getEnum(Class<T> enumClass, String path){
        return Enum.valueOf(enumClass, getString(path));
    }
    default void set(String path, Enum value){
        set(path, value.name());
    }

    default <T extends Enum<T>> List<T> getEnumList(Class<T> enumClass, String path){
        List<T> enums = new ArrayList<>();
        getStringList(path).forEach(s -> Enum.valueOf(enumClass, s));
        return enums;
    }
    default void setEnumList(String path, List<Enum> values){
        values.forEach(e -> set(path, e));
    }

    default Long getTime(String path){
        return Serializer.TIME.deserialize(getString(path));
    }
    default void setTime(String path, Long value){
        set(path, Serializer.TIME.serialize(value));
    }

    default Dictionary getDictionary(String path) {

        Dictionary dictionary = new Dictionary();
        ConfigurationSection configurationSection = getConfigurationSection(path);

        for (String key : configurationSection.getKeys(false))
            dictionary.put(key, getString(path + "." + key));

        return dictionary;
    }

    // TODO: Add other types.
}
