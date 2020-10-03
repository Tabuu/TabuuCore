package nl.tabuu.tabuucore.inventory.ui.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ElementGroup extends Element {

    private List<Element> _elements;

    public ElementGroup(){
        _elements = new ArrayList<>();
    }

    public ElementGroup(Element... elements){
        this();
        add(elements);
    }

    public void add(Collection<Element> elements) {
        _elements.addAll(elements);
    }

    public void add(Element... elements){
        _elements.addAll(Arrays.asList(elements));
    }

    public List<Element> getElements(){
        return _elements;
    }
}
