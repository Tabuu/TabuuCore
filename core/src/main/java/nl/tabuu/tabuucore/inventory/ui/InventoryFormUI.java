package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.*;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class InventoryFormUI extends InventoryUI {

    private Map<Vector2f, Element> _elements;

    public InventoryFormUI(String title, InventorySize size) {
        super(title, size);
        _elements = new HashMap<>();
        addBlockedAction(InventoryAction.COLLECT_TO_CURSOR);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(!event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;

        for(Element element : getElements(true)) {

            if(element instanceof ItemInput) {
                ItemInput itemInput = (ItemInput) element;


            }
        }
    }

    @Override
    public void onClickUI(InventoryClickEvent event) {
        event.setCancelled(true);

        Vector2f position = getSize().slotToVector(event.getSlot());
        Element element = _elements.get(position);

        if(element instanceof IClickable && element.isEnabled()) {
            IClickable clickable = (IClickable) element;
            clickable.click(event);
            updateElement(position);
        }
    }

    @Override
    public void onDragUI(InventoryDragEvent event) {
        event.setCancelled(true);

        //TODO: ItemInput?
    }

    @Override
    protected void onDraw(){
        _elements.keySet().forEach(this::updateElement);
    }

    public void updateElement(Vector2f position){
        Element element = _elements.get(position);

        if(element instanceof StyleableElement){
            StyleableElement<?> styleable = (StyleableElement<?>) element;
            styleable.updateStyle();
            setItemAt(position, styleable.getDisplayItem());
        }
    }

    public void setElement(Vector2f position, Element element){
        _elements.put(position, element);
        element.setPosition(position);
    }

    private List<Element> getElements(boolean deep) {
        List<Element> elements = new ArrayList<>();

        for(Element element : _elements.values()) {
            elements.add(element);

            if(deep && element instanceof ElementGroup) {
                ElementGroup group = (ElementGroup) element;
                elements.addAll(group.getElements());
            }
        }

        return elements;
    }
}
