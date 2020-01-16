package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.UUID;

public class OfflinePlayerSerializer extends AbstractStringSerializer<OfflinePlayer> {
    @Override
    public String serialize(OfflinePlayer object) {
        return object.getName();
    }

    @Override
    public OfflinePlayer deserialize(String string) {
        if(string.length() > 16){
            try{
                UUID uuid = UUID.fromString(string);
                return Bukkit.getOfflinePlayer(uuid);
            } catch (IllegalArgumentException e){
                return null;
            }
        }
        else return Arrays
                    .stream(Bukkit.getOfflinePlayers())
                    .filter(p -> p.getName().equalsIgnoreCase(string))
                    .findAny().orElse(null);
    }
}