package nl.tabuu.tabuucore.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import nl.tabuu.tabuucore.nms.wrapper.ITitleUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUtil implements ITitleUtil {
    public void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(json), (byte) 1);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }

    public void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(json), (byte) 0);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }

    public void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(json), (byte) 2);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }
}
