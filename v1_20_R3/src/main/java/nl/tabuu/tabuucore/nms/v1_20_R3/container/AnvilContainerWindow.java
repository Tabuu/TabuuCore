package nl.tabuu.tabuucore.nms.v1_20_R3.container;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.level.World;
import nl.tabuu.tabuucore.debug.Debug;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

public class AnvilContainerWindow extends ContainerWindow implements IAnvilContainerWindow {

    private final ContainerAnvil _container;

    protected AnvilContainerWindow(Player player) {
        super(player);

        World world = getEntityPlayer().cK();
        BlockPosition blockPosition = new BlockPosition(0, 0, 0);
        _container = new ContainerAnvil(getId(), getEntityPlayer().fS(), ContainerAccess.a(world, blockPosition));
        _container.setTitle(IChatBaseComponent.a(" "));
        _container.checkReachable = false;
    }

    @Override
    protected ContainerAnvil getContainer() {
        return _container;
    }

    @Override
    protected void sendContainerWindowOpenPacket() {
        getEntityPlayer().c.b(new PacketPlayOutOpenWindow(getId(), Containers.i, IChatBaseComponent.b(InventoryType.ANVIL.getDefaultTitle())));
    }

    @Override
    public String getRenameText() {
        if(!(getInventory() instanceof AnvilInventory))
            return "";

        AnvilInventory anvilInventory = (AnvilInventory) getInventory();
        return anvilInventory.getRenameText();
    }
}