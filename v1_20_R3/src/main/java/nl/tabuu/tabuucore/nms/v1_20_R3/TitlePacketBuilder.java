package nl.tabuu.tabuucore.nms.v1_20_R3;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.*;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitlePacketBuilder extends nl.tabuu.tabuucore.nms.wrapper.TitlePacketBuilder {

    @Override
    protected void sendReset(Player player) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ClientboundClearTitlesPacket resetPacket = new ClientboundClearTitlesPacket(true);
        craftPlayer.getHandle().c.a(resetPacket);
    }

    @Override
    protected void sendActionBar(Player player, String json) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ClientboundSetActionBarTextPacket actionBarPacket = new ClientboundSetActionBarTextPacket(IChatBaseComponent.ChatSerializer.a(json));
        craftPlayer.getHandle().c.a(actionBarPacket);
    }

    @Override
    protected void sendTitlePacket(Player player, String json) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ClientboundSetTitleTextPacket titlePacket = new ClientboundSetTitleTextPacket(IChatBaseComponent.ChatSerializer.a(json));
        craftPlayer.getHandle().c.a(titlePacket);
    }

    @Override
    protected void sendSubTitlePacket(Player player, String json) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ClientboundSetSubtitleTextPacket subTitlePacket = new ClientboundSetSubtitleTextPacket(IChatBaseComponent.ChatSerializer.a(json));
        craftPlayer.getHandle().c.a(subTitlePacket);
    }

    @Override
    protected void sendTimings(Player player, int fadeIn, int stay, int fadeOut) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ClientboundSetTitlesAnimationPacket timingsPacket = new ClientboundSetTitlesAnimationPacket(fadeIn, stay, fadeOut);
        craftPlayer.getHandle().c.a(timingsPacket);
    }
}