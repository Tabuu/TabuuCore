package nl.tabuu.tabuucore.nms.v1_17_R1.container;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.level.World;
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
        _container = new ContainerAnvil(getId(), getEntityPlayer().getInventory(), ContainerAccess.at(world, blockPosition));
        _container.checkReachable = false;
    }

    @Override
    protected ContainerAnvil getContainer() {
        return _container;
    }

    @Override
    protected void sendContainerWindowOpenPacket() {
        getEntityPlayer().b.sendPacket(new PacketPlayOutOpenWindow(getId(), Containers.h, new ChatComponentText(InventoryType.ANVIL.getDefaultTitle())));
    }

    @Override
    public String getRenameText() {
        if(!(getInventory() instanceof AnvilInventory))
            return "";

        AnvilInventory anvilInventory = (AnvilInventory) getInventory();
        return anvilInventory.getRenameText();
    }
}