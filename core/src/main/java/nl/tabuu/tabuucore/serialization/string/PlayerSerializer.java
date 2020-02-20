package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerSerializer extends AbstractStringSerializer<Player> {

    @Override
    public String serialize(Player object) {
        return object.getUniqueId().toString();
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
