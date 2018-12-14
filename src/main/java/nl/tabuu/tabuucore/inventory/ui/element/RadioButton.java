package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.ToggleableStyle;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class RadioButton extends Checkbox {

    private ElementGroup _group;

    public RadioButton(ToggleableStyle style, ElementGroup group) {
        this(style, group, null);
    }

    public RadioButton(ToggleableStyle style, ElementGroup group, BiConsumer<Player, Boolean> onChange) {
        super(style, onChange);
        _group = group;
        _group.addElements(this);
    }

    @Override
    public void click(Player player) {
        if(!getValue()){
            _group.getElements().forEach((e) -> {
                if(e instanceof RadioButton && e != this) {
                    ((RadioButton) e).setValue(false);
                    ((RadioButton) e).getConsumer().accept(player, false);
                    ((RadioButton) e).updateStyle();
                }
            });
            setValue(true);

            if(getConsumer() != null)
                getConsumer().accept(player, true);
        }
    }
}
