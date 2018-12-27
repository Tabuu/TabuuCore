package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Element;
import nl.tabuu.tabuucore.inventory.ui.element.IClickable;
import nl.tabuu.tabuucore.inventory.ui.element.StylableElement;
import nl.tabuu.tabuucore.inventory.ui.graphics.InventoryCanvas;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;

import java.util.HashMap;
import java.util.Map;


public abstract class InventoryFormUI extends InventoryUI {

    private Map<Vector2f, Element> _elements;

    public InventoryFormUI(String title, InventorySize size) {
        super(title, size);
        _elements = new HashMap<>();
        addBlockedAction(InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.COLLECT_TO_CURSOR);
    }

    @Override
    public void onClickUI(Player player, InventoryUIClick click) {
        click.setCanceled(true);

        Vector2f position = InventoryCanvas.vectorToSlot(click.getSlot());
        Element element = _elements.get(position);

        if(element instanceof IClickable && element.isEnabled()){
            IClickable clickable = (IClickable) element;
            clickable.click(player, click);
            updateElement(position);
        }
    }

    @Override
    public void onDragUI(Player player, InventoryUIDrag drag) {
        drag.setCanceled(true);

        //TODO: ItemInput?
    }

    @Override
    protected void draw(){
        _elements.keySet().forEach(this::updateElement);
    }

    public void updateElement(Vector2f position){
        Element element = _elements.get(position);

        if(element instanceof StylableElement){
            StylableElement stylable = (StylableElement) element;
            stylable.updateStyle();
            setItemAt(position, stylable.getDisplayItem());
        }
    }

    public void setElement(Vector2f position, Element element){
        _elements.put(position, element);
        element.setPosition(position);
    }
}
