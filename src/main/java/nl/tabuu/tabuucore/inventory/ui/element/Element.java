package nl.tabuu.tabuucore.inventory.ui.element;

public abstract class Element {
    private boolean _enabled;

    public Element(){
        _enabled = true;
    }

    public boolean isEnabled(){
        return _enabled;
    }

    public void setEnabled(boolean value){
        _enabled = value;
    }
}
