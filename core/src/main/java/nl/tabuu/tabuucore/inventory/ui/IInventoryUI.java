package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public interface IInventoryUI {
    /**
     * Is called when a player drags items though the ui part of the inventory view.
     *
     * @param drag The drag event called.
     */
    void onDragUI(InventoryDragEvent drag);

    /**
     * Is called when a player drags items though the inventory.
     *
     * @param drag The drag event called.
     */
    void onDrag(InventoryDragEvent drag);

    /**
     * Is called when a player clicks in the ui part of the inventory view.
     *
     * @param click The click event called.
     */
    void onClickUI(InventoryClickEvent click);

    /**
     * Is called when a player clicks in the inventory.
     *
     * @param click The click event called.
     */
    void onClick(InventoryClickEvent click);

    /**
     * Is called when a player opens the ui.
     *
     * @param player The player opening the ui.
     */
    void onOpen(Player player);

    /**
     * Is called when a player closes the ui.
     *
     * @param player The player closing the ui.
     */
    void onClose(Player player);

    /**
     * Sets the inventory of the ui.
     *
     * @param inventory The inventory to set.
     */
    void setInventory(Inventory inventory);

    /**
     * Returns the inventory represented by the ui.
     *
     * @return The inventory represented by the ui.
     */
    Inventory getInventory();
}