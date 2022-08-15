package nl.tabuu.tabuucore.inventory.ui.element;

public interface IValuable<T> {
    T getValue();
    void setValue(T value);
}