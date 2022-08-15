package nl.tabuu.tabuucore.nms.v1_18_R1.container;

import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.inventory.Container;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class ContainerWindow extends nl.tabuu.tabuucore.nms.wrapper.container.ContainerWindow {

    protected ContainerWindow(Player player) {
        super(player, ((CraftPlayer) player).getHandle().nextContainerCounter());
    }

    protected abstract Container getContainer();

    @Override
    public Inventory getInventory() {
        return getContainer().getBukkitView().getTopInventory();
    }

    @Override
    protected void sendContainerWindowClosePacket() {
        getEntityPlayer().b.a(new PacketPlayOutCloseWindow(getId()));
    }

    @Override
    protected void callCloseCurrentInventoryEvent() {
        CraftEventFactory.handleInventoryCloseEvent(getEntityPlayer());
    }

    @Override
    protected void setPlayerWindowToPlayerInventory() {
        getEntityPlayer().bW = getEntityPlayer().bV;
    }

    @Override
    protected void setPlayWindowToContainerInventory() {
        getEntityPlayer().bW = getContainer();
        getEntityPlayer().a(getContainer());
    }

    protected EntityPlayer getEntityPlayer() {
        return ((CraftPlayer) getPlayer()).getHandle();
    }
}