package nl.tabuu.tabuucore.nms.v1_17_R1;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import nl.tabuu.tabuucore.debug.Debug;
import nl.tabuu.tabuucore.nms.wrapper.IInventoryUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.event.CraftEventFactory;
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
        playerToNMS(player).b.sendPacket(new PacketPlayOutOpenWindow(containerId, Containers.h, new ChatComponentText(InventoryType.ANVIL.getDefaultTitle())));
    }

    @Override
    public void sendPacketCloseWindow(Player player, int containerId) {
        playerToNMS(player).b.sendPacket(new PacketPlayOutCloseWindow(containerId));
    }

    @Override
    public void setActiveContainerToDefault(Player player) {
        playerToNMS(player).bV = playerToNMS(player).bU;
    }

    @Override
    public void setActiveContainer(Player player, IContainerWindow container) {
        playerToNMS(player).bV = (Container) container.getNMSContainer();
    }

    @Override
    public void addActiveContainerSlotListener(Player player, IContainerWindow container) {
        playerToNMS(player).initMenu((Container) container.getNMSContainer());
    }

    private EntityPlayer playerToNMS(Player player){
        return ((CraftPlayer) player).getHandle();
    }
}
