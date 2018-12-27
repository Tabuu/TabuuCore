package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface IInventoryUI {

    void onDragUI(Player player, InventoryUIDrag drag);
    void onDrag(Player player, InventoryUIDrag drag);

    void onClickUI(Player player, InventoryUIClick click);
    void onClick(Player player, InventoryUIClick click);

    void onOpen(Player player);
    void onClose(Player player);

    void setInventory(Inventory inventory);
    Inventory getInventory();

}
