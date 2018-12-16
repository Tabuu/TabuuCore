package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.inventory.InventoryUIClick;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface IInventoryUI {

    boolean onClick(Player player, InventoryUIClick click);
    void onOpen(Player player);
    void onClose(Player player);
    Inventory getInventory();

}
