package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerSerializer extends OfflinePlayerSerializer {

    @Override
    public String serialize(OfflinePlayer object) {
        return object.getName();
    }

    @Override
    public Player deserialize(String string) {
        OfflinePlayer offlinePlayer = Serializer.OFFLINE_PLAYER.deserialize(string);

        if(offlinePlayer == null || !offlinePlayer.isOnline())
            return null;
        else
            return (Player) offlinePlayer;
    }
}
