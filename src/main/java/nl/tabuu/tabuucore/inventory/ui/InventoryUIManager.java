package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryUIManager {

    private HashMap<Inventory, InventoryUI> _uis;

    public InventoryUIManager(){
        _uis = new HashMap<>();
    }

    public void register(InventoryUI ui){
        _uis.put(ui.getInventory(), ui);
    }

    public InventoryUI get(Inventory inventory){
        return _uis.get(inventory);
    }

}
