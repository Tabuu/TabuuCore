package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.inventory.Inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

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

    public void forEach(Consumer<InventoryUI> consumer){
        _uis.values().forEach(consumer);
    }

    public InventoryUI get(Inventory inventory){
        return _uis.get(inventory);
    }

}
