package nl.tabuu.tabuucore.inventory.ui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

@Deprecated /* 3rd June 2020 */
public class InventoryUIClick {

    private ClickType _clickType;
    private InventoryAction _inventoryAction;
    private InventoryType.SlotType _slotType;
    private int _hotbarButton, _rawSlot, _slot;
    private boolean _isRightClick, _isLeftClick, _isShiftClick;
    private ItemStack _clickedItem, _cursorItem;
    private boolean _canceled;
    private InventoryClickEvent _handle;

    public InventoryUIClick(InventoryClickEvent event){
        _clickType          = event.getClick();
        _inventoryAction    = event.getAction();
        _slotType           = event.getSlotType();
        _hotbarButton       = event.getHotbarButton();
        _rawSlot            = event.getRawSlot();
        _slot               = event.getSlot();
        _isRightClick       = event.isRightClick();
        _isLeftClick        = event.isLeftClick();
        _isShiftClick       = event.isShiftClick();
        _clickedItem        = event.getCurrentItem();
        _cursorItem         = event.getCursor();
        _canceled           = false;

        _handle = event;
    }

    public ClickType getClickType() {
        return _clickType;
    }

    public InventoryAction getInventoryAction() {
        return _inventoryAction;
    }

    public InventoryType.SlotType getSlotType() {
        return _slotType;
    }

    public int getHotbarButton() {
        return _hotbarButton;
    }

    public int getRawSlot() {
        return _rawSlot;
    }

    public int getSlot() {
        return _slot;
    }

    public boolean isRightClick() {
        return _isRightClick;
    }

    public boolean isLeftClick() {
        return _isLeftClick;
    }

    public boolean isShiftClick() {
        return _isShiftClick;
    }

    public ItemStack getClickedItem() {
        return _clickedItem;
    }

    public ItemStack getCursorItem() {
        return _cursorItem;
    }

    public void setCanceled(boolean canceled){
        _canceled = canceled;
    }

    public boolean isCanceled(){
        return _canceled;
    }

    public InventoryClickEvent getHandle() {
        return _handle;
    }
}
