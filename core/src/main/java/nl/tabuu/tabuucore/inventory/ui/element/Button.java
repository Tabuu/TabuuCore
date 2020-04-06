package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class Button extends StyleableElement<Style> implements IClickable {

    private Consumer<Player> _left, _right;
    private Style _style;

    public Button(Style style, Consumer<Player> onLeftClick, Consumer<Player> onRightClick){
        super(style);
        _left = onLeftClick;
        _right = onRightClick;
        _style = style;
    }

    public Button(Style style, Consumer<Player> onClick) {
        this(style, onClick, onClick);
    }

    public Button(Style style){
        this(style, null);
    }

    /**
     * @return The left click consumer.
     * @deprecated Deprecated in favor of {@link #getLeftConsumer()} 17th March 2020
     */
    @Deprecated
    protected Consumer<Player> getConsumer(){
        return getLeftConsumer();
    }

    protected Consumer<Player> getLeftConsumer() {
        return _left;
    }

    protected Consumer<Player> getRightConsumer() {
        return _right;
    }

    @Override
    public void click(Player player, InventoryUIClick click) {
        if(click.isLeftClick() && getLeftConsumer() != null)
            getLeftConsumer().accept(player);

        if(click.isRightClick() && getRightConsumer() != null)
            getRightConsumer().accept(player);
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
