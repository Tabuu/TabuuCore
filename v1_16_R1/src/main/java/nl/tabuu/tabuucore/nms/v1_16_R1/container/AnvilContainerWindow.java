package nl.tabuu.tabuucore.nms.v1_16_R1.container;

import net.minecraft.server.v1_16_R1.*;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

public class AnvilContainerWindow extends ContainerWindow implements IAnvilContainerWindow {

    private final ContainerAnvil _container;

    protected AnvilContainerWindow(Player player) {
        super(player);

        World world = getEntityPlayer().getWorld();
        BlockPosition blockPosition = new BlockPosition(0, 0, 0);
        _container = new ContainerAnvil(getId(), getEntityPlayer().inventory, ContainerAccess.at(world, blockPosition));
        _container.checkReachable = false;
    }

    @Override
    protected ContainerAnvil getContainer() {
        return _container;
    }

    @Override
    protected void sendContainerWindowOpenPacket() {
        getEntityPlayer().playerConnection.sendPacket(new PacketPlayOutOpenWindow(getId(), Containers.ANVIL, new ChatComponentText(InventoryType.ANVIL.getDefaultTitle())));
    }

    @Override
    public String getRenameText() {
        if(!(getInventory() instanceof AnvilInventory))
            return "";

        AnvilInventory anvilInventory = (AnvilInventory) getInventory();
        return anvilInventory.getRenameText();
    }
}