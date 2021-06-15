package nl.tabuu.tabuucore.nms.v1_17_R1.container;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.level.World;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;

public class AnvilContainerWindow extends ContainerWindow implements IAnvilContainerWindow {

    private Container _container;

    protected AnvilContainerWindow(Player player) {
        super(player);

        World world = playerToNMS(player).getWorld();
        BlockPosition blockPosition = new BlockPosition(0, 0, 0);
        _container = new ContainerAnvil(_windowId, playerToNMS(player).getInventory(), ContainerAccess.at(world, blockPosition));
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
