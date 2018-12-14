package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.element.Element;
import nl.tabuu.tabuucore.inventory.ui.element.IClickable;
import nl.tabuu.tabuucore.inventory.ui.element.StylableElement;
import nl.tabuu.tabuucore.inventory.ui.graphics.InventoryCanvas;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.HashMap;
import java.util.Map;


public abstract class InventoryFormUI extends InventoryUI {

    private Map<Vector2f, Element> _elements;

    public InventoryFormUI(String title, InventorySize size) {
        super(title, size);
        _elements = new HashMap<>();
        addBlockedAction(InventoryAction.MOVE_TO_OTHER_INVENTORY);
    }

    @Override
    public boolean onClick(Player player, InventoryUIClick click) {
        Vector2f position = InventoryCanvas.vectorToSlot(click.getSlot());
        Element element = _elements.get(position);

        if(element instanceof IClickable && element.isEnabled()){
            IClickable clickable = (IClickable) element;
            clickable.click(player);
            updateElement(position);
        }

        return true;
    }

    @Override
    protected void draw(){
        _elements.keySet().forEach(this::updateElement);
    }

    protected void updateElement(Vector2f position){
        Element element = _elements.get(position);

        if(element instanceof StylableElement){
            StylableElement stylable = (StylableElement) element;
            stylable.updateStyle();
            setItemAt(position, stylable.getDisplayItem());
        }
    }

    public void addElement(Vector2f position, Element element){
        _elements.put(position, element);
    }
}
