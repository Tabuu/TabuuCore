package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.ToggleableStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.BiConsumer;

public abstract class ToggleableElement extends StyleableElement<ToggleableStyle> implements IClickable, IValuable<Boolean> {

    private boolean _value;
    private BiConsumer<Player, Boolean> _consumer;

    public ToggleableElement(ToggleableStyle style) {
        this(style, null);
    }

    public ToggleableElement(ToggleableStyle style, BiConsumer<Player, Boolean> onClick) {
        super(style);
        _consumer = onClick;
        _value = false;
    }

    protected BiConsumer<Player, Boolean> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        _value ^= true;

        if(_consumer != null)
            _consumer.accept(player, _value);
    }

    @Override
    public void updateStyle() {
        super.updateStyle();

        if(isEnabled())
            setDisplayItem(_value ? getStyle().getOn() : getStyle().getOff());
    }

    @Override
    public Boolean getValue(){
        return _value;
    }

    @Override
    public void setValue(Boolean value){
        _value = value;
    }
}