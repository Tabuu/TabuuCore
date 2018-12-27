package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

public class InventoryUIDrag {

    private ItemStack _cursorItem, _oldCursorItem;
    private boolean _canceled;
    private Set<Integer> _slots, _rawSlots;
    private Map<Integer, ItemStack> _newItems;
    private DragType _dragType;

    public InventoryUIDrag(InventoryDragEvent event){
        _cursorItem         = event.getCursor();
        _oldCursorItem      = event.getOldCursor();
        _slots              = event.getInventorySlots();
        _rawSlots           = event.getRawSlots();
        _newItems           = event.getNewItems();
        _dragType           = event.getType();
        _canceled           = false;
    }

    public ItemStack getCursorItem() {
        return _cursorItem;
    }

    public ItemStack getOldCursorItem(){
        return _oldCursorItem;
    }

    public Set<Integer> getSlots(){
        return _slots;
    }

    public Set<Integer> getRawSlots(){
        return _rawSlots;
    }

    public Map<Integer, ItemStack> getNewItems(){
        return _newItems;
    }

    public DragType getDragType(){
        return _dragType;
    }

    public void setCanceled(boolean canceled){
        _canceled = canceled;
    }

    public boolean isCanceled(){
        return _canceled;
    }

}
