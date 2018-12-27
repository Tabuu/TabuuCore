package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class Button extends StylableElement<Style> implements IClickable {

    private Consumer<Player> _consumer;
    private Style _style;

    public Button(Style style){
        this(style, null);
    }

    public Button(Style style, Consumer<Player> onClick){
        super(style);
        _consumer = onClick;
        _style = style;
    }

    protected Consumer<Player> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(Player player, InventoryUIClick click) {
        if(_consumer != null)
            _consumer.accept(player);
    }

    @Override
    public Style getStyle() {
        return _style;
    }

    @Override
    public void updateStyle() {
        super.updateStyle();
    }
}
