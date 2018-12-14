package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.inventory.ItemStack;

public abstract class StylableElement<S extends Style> extends Element {

    private S _style;
    private ItemStack _currentDisplayItem;

    public StylableElement(S style){
        _style = style;
        _currentDisplayItem = _style.getEnabled();
    }

    public S getStyle() {
        return _style;
    }

    public ItemStack getDisplayItem(){
        return _currentDisplayItem;
    }

    protected void setDisplayItem(ItemStack item){
        _currentDisplayItem = item;
    }

    public void updateStyle(){
        if(!isEnabled())
            setDisplayItem(_style.getDisabled());
    }
}
