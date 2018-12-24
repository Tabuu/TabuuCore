package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryUIManager {

    private HashMap<Inventory, InventoryUI> _uis;

    public InventoryUIManager(){
        _uis = new HashMap<>();
    }

    public void register(InventoryUI ui){
        if(_uis.values().contains(ui)){
            unregister(ui);
            register(ui);
        }
        _uis.put(ui.getInventory(), ui);
    }

    public void unregister(Inventory inventory){
        _uis.remove(inventory);
    }

    public void unregister(InventoryUI ui){
        _uis.keySet().stream()
                .filter(inv -> get(inv).equals(ui))
                .findAny()
                .ifPresent(this::unregister);
    }

    public InventoryUI get(Inventory inventory){
        return _uis.get(inventory);
    }

}
