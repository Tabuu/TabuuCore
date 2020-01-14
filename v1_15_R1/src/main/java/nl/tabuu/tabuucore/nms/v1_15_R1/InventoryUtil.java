package nl.tabuu.tabuucore.nms.v1_15_R1;

import net.minecraft.server.v1_15_R1.*;
import nl.tabuu.tabuucore.nms.wrapper.IInventoryUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

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
        playerToNMS(player).playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, Containers.ANVIL, new ChatComponentText(InventoryType.ANVIL.getDefaultTitle())));
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
