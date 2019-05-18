package nl.tabuu.tabuucore.nms.v1_8_R3.container;

import net.minecraft.server.v1_8_R3.*;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;

public class AnvilContainerWindow extends ContainerWindow implements IAnvilContainerWindow {

    private Container _container;

    protected AnvilContainerWindow(Player player) {
        super(player);

        EntityPlayer entityPlayer = playerToNMS(player);
        World world = entityPlayer.world;
        BlockPosition blockPosition = new BlockPosition(0, 0, 0);
        _container = new ContainerAnvil(entityPlayer.inventory, world, blockPosition, entityPlayer);
        _container.windowId = _windowId;
        _container.checkReachable = false;
    }

    @Override
    public Container getNMSContainer() {
        return _container;
    }

    @Override
    public String getRenameText() {

        if(!(getInventory() instanceof AnvilInventory))
            return "";

        AnvilInventory anvilInventory = (AnvilInventory) getInventory();
        return anvilInventory.getItem(2).getItemMeta().getDisplayName();
    }
}
