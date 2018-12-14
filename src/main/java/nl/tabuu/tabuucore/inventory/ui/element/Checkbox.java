package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.ToggleableStyle;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class Checkbox extends ToggleableElement implements IClickable {

    public Checkbox(ToggleableStyle style) {
        super(style);
    }

    public Checkbox(ToggleableStyle style, BiConsumer<Player, Boolean> onClick) {
        super(style, onClick);
    }
}
