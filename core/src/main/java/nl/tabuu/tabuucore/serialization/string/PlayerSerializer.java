package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PlayerSerializer extends AbstractStringSerializer<Player> {

    @Override
    public String serialize(Player object) {
        if(Objects.isNull(object)) return null;

        return object.getUniqueId().toString();
    }

    @Override
    public Player deserialize(String string) {
        if(Objects.isNull(string)) return null;

        OfflinePlayer offlinePlayer = Serializer.OFFLINE_PLAYER.deserialize(string);

        if(offlinePlayer == null || !offlinePlayer.isOnline())
            return null;
        else
            return (Player) offlinePlayer;
    }
}
