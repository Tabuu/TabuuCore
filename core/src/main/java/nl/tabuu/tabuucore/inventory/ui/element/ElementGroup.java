package nl.tabuu.tabuucore.inventory.ui.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementGroup extends Element {

    private List<Element> _elements;

    public ElementGroup(Element... elements){
        this();
        addElements(elements);
    }

    public ElementGroup(){
        _elements = new ArrayList<>();
    }

    public void addElements(Element... elements){
        _elements.addAll(Arrays.asList(elements));
    }

    public List<Element> getElements(){
        return _elements;
    }
}
