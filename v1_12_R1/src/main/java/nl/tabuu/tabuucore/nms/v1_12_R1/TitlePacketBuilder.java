package nl.tabuu.tabuucore.nms.v1_12_R1;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitlePacketBuilder extends nl.tabuu.tabuucore.nms.wrapper.TitlePacketBuilder {

    @Override
    protected void sendReset(Player player) {
        sendPacket(player, PacketPlayOutTitle.EnumTitleAction.RESET, null);
    }

    @Override
    protected void sendActionBar(Player player, String json) {
        sendPacket(player, PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, json);
    }

    @Override
    protected void sendTitlePacket(Player player, String json) {
        sendPacket(player, PacketPlayOutTitle.EnumTitleAction.TITLE, json);
    }

    @Override
    protected void sendSubTitlePacket(Player player, String json) {
        sendPacket(player, PacketPlayOutTitle.EnumTitleAction.SUBTITLE, json);
    }

    @Override
    protected void sendTimings(Player player, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PacketPlayOutTitle timingsPacket = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        craftPlayer.getHandle().playerConnection.sendPacket(timingsPacket);
    }

    private void sendPacket(Player player, PacketPlayOutTitle.EnumTitleAction type, String value) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        PacketPlayOutTitle actionBarPacket = new PacketPlayOutTitle(type, IChatBaseComponent.ChatSerializer.a(value));
        craftPlayer.getHandle().playerConnection.sendPacket(actionBarPacket);
    }
}