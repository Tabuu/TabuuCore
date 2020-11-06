package nl.tabuu.tabuucore.nms.v1_16_R3.container;

import net.minecraft.server.v1_16_R3.*;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;

public class AnvilContainerWindow extends ContainerWindow implements IAnvilContainerWindow {

    private Container _container;

    protected AnvilContainerWindow(Player player) {
        super(player);

        World world = playerToNMS(player).world;
        BlockPosition blockPosition = new BlockPosition(0, 0, 0);
        _container = new ContainerAnvil(_windowId, playerToNMS(player).inventory, ContainerAccess.at(world, blockPosition));
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
        return anvilInventory.getRenameText();
    }
}
