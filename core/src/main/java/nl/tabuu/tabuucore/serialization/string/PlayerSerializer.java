package nl.tabuu.tabuucore.serialization.string;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerSerializer extends OfflinePlayerSerializer {

    @Override
    public String serialize(OfflinePlayer object) {
        return object.getName();
    }

    @Override
    public Player deserialize(String string) {
        return Bukkit.getPlayer(string);
    }
}
