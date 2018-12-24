package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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

    default Material getMaterial(String path){
        return Material.valueOf(getString(path));
    }
    default void set(String path, Material value){
        set(path, value.name());
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
