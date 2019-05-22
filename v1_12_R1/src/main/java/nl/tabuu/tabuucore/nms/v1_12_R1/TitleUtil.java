package nl.tabuu.tabuucore.nms.v1_12_R1;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import nl.tabuu.tabuucore.nms.wrapper.ITitleUtil;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUtil implements ITitleUtil {
    public void sendSubTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        send(player,PacketPlayOutTitle.EnumTitleAction.SUBTITLE, json, fadeIn, stay, fadeOut);
    }

    public void sendTitle(Player player, String json, int fadeIn, int stay, int fadeOut) {
        send(player,PacketPlayOutTitle.EnumTitleAction.TITLE, json, fadeIn, stay, fadeOut);
    }

    public void sendActionbar(Player player, String json, int fadeIn, int stay, int fadeOut) {
        send(player,PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, json, fadeIn, stay, fadeOut);
    }

    private void send(Player player, PacketPlayOutTitle.EnumTitleAction type, String json, int fadeIn, int stay, int fadeOut){
        PacketPlayOutTitle packet = new PacketPlayOutTitle(type, IChatBaseComponent.ChatSerializer.a(json), fadeIn, stay, fadeOut);

        CraftPlayer craftPlayer = (CraftPlayer) player;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }
}
