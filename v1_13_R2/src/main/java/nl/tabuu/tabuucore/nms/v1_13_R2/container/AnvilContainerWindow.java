package nl.tabuu.tabuucore.nms.v1_13_R2.container;

import net.minecraft.server.v1_13_R2.*;
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
        _container = new ContainerAnvil(getEntityPlayer().inventory, world, blockPosition, getEntityPlayer());
        _container.windowId = getId();
        _container.checkReachable = false;
    }

    @Override
    protected ContainerAnvil getContainer() {
        return _container;
    }

    @Override
    protected void sendContainerWindowOpenPacket() {
        getEntityPlayer().playerConnection.sendPacket(new PacketPlayOutOpenWindow(getId(), "minecraft:anvil", new ChatMessage(Blocks.ANVIL.a() + ".name")));
    }

    @Override
    public String getRenameText() {
        if(!(getInventory() instanceof AnvilInventory))
            return "";

        AnvilInventory anvilInventory = (AnvilInventory) getInventory();
        return anvilInventory.getRenameText();
    }
}