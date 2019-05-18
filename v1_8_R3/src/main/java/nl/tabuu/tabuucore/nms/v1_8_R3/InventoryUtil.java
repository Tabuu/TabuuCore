package nl.tabuu.tabuucore.nms.v1_8_R3;

import nl.tabuu.tabuucore.nms.wrapper.IInventoryUtil;
import net.minecraft.server.v1_8_R3.*;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;

public class InventoryUtil implements IInventoryUtil {
    @Override
    public int getNextContainerId(Player player) {
        return playerToNMS(player).nextContainerCounter();
    }

    @Override
    public void handleInventoryCloseEvent(Player player) {
        CraftEventFactory.handleInventoryCloseEvent(playerToNMS(player));
    }

    @Override
    public void sendPacketOpenWindow(Player player, int containerId) {
        playerToNMS(player).playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage(Blocks.ANVIL.a() + ".name")));
    }

    @Override
    public void sendPacketCloseWindow(Player player, int containerId) {
        playerToNMS(player).playerConnection.sendPacket(new PacketPlayOutCloseWindow(containerId));
    }

    @Override
    public void setActiveContainerToDefault(Player player) {
        playerToNMS(player).activeContainer = playerToNMS(player).defaultContainer;
    }

    @Override
    public void setActiveContainer(Player player, IContainerWindow container) {
        playerToNMS(player).activeContainer = (Container) container.getNMSContainer();
    }

    @Override
    public void addActiveContainerSlotListener(Player player, IContainerWindow container) {
        ((Container) container.getNMSContainer()).addSlotListener(playerToNMS(player));
    }

    private EntityPlayer playerToNMS(Player player){
        return ((CraftPlayer) player).getHandle();
    }
}
