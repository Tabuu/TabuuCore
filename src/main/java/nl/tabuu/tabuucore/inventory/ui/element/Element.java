package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryFormUI;
import nl.tabuu.tabuucore.util.vector.Vector2f;

public abstract class Element {
    private boolean _enabled;
    private Vector2f _position;

    public Element(){
        _enabled = true;
        _position = new Vector2f(0, 0);
    }

    public boolean isEnabled(){
        return _enabled;
    }

    public void setEnabled(boolean value){
        _enabled = value;
    }

    public void update(InventoryFormUI formUI){
        formUI.updateElement(_position);
    }

    public void setPosition(Vector2f position){
        _position = position;
    }

    public Vector2f getPosition(){
        return _position;
    }
}
