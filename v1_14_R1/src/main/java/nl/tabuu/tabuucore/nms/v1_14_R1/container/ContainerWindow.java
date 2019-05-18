package nl.tabuu.tabuucore.nms.v1_14_R1.container;

import net.minecraft.server.v1_14_R1.Container;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import nl.tabuu.tabuucore.nms.v1_14_R1.InventoryUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class ContainerWindow implements IContainerWindow {

    protected InventoryUtil _util;

    protected int _windowId;
    protected Player _player;

    protected ContainerWindow(Player player){
        _util = new InventoryUtil();

        _player = player;
        _windowId = _util.getNextContainerId(player);
    }

    @Override
    public abstract Container getNMSContainer();

    protected EntityPlayer playerToNMS(Player player){
        return ((CraftPlayer) player).getHandle();
    }

    @Override
    public Player getPlayer() {
        return _player;
    }

    @Override
    public int getWindowId() {
        return _windowId;
    }

    @Override
    public void open() {
        _util.handleInventoryCloseEvent(getPlayer());
        _util.setActiveContainerToDefault(getPlayer());

        _util.sendPacketOpenWindow(getPlayer(), getWindowId());

        _util.setActiveContainer(getPlayer(), this);
        _util.addActiveContainerSlotListener(getPlayer(), this);
    }

    @Override
    public void close() {
        _util.sendPacketCloseWindow(getPlayer(), getWindowId());
    }

    @Override
    public Inventory getInventory() {
        return getNMSContainer().getBukkitView().getTopInventory();
    }
}
