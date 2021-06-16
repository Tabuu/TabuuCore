package nl.tabuu.tabuucore.nms.wrapper.container;

import org.bukkit.entity.Player;

public abstract class ContainerWindow implements IContainerWindow {

    private final Player _player;
    private final int _id;

    protected ContainerWindow(Player player, int id) {
        _player = player;
        _id = id;
    }

    public void open() {
        // Closes current open inventory, if any.
        callCloseCurrentInventoryEvent();
        setPlayerWindowToPlayerInventory();

        // Opens the container inventory.
        sendContainerWindowOpenPacket();
        setPlayWindowToContainerInventory();
    }

    public void close() {
        // Close current inventory.
        callCloseCurrentInventoryEvent();
        sendContainerWindowClosePacket();

        // Sets the current inventory to the players inventory.
        setPlayerWindowToPlayerInventory();
    }

    public Player getPlayer() {
        return _player;
    }

    protected int getId() {
        return _id;
    }

    protected abstract void sendContainerWindowOpenPacket();

    protected abstract void sendContainerWindowClosePacket();

    protected abstract void callCloseCurrentInventoryEvent();

    protected abstract void setPlayerWindowToPlayerInventory();

    protected abstract void setPlayWindowToContainerInventory();
}