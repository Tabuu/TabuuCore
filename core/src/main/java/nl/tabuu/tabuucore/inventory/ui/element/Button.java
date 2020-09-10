package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class Button extends StyleableElement<Style> implements IClickable {

    private Consumer<Player> _left, _right;

    public Button(Style style, Consumer<Player> onLeftClick, Consumer<Player> onRightClick) {
        super(style);
        _left = onLeftClick;
        _right = onRightClick;
    }

    public Button(Style style, Consumer<Player> onClick) {
        this(style, onClick, onClick);
    }

    public Button(Style style) {
        this(style, null);
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.isLeftClick() && getLeftConsumer() != null)
            getLeftConsumer().accept(player);

        if (event.isRightClick() && getRightConsumer() != null)
            getRightConsumer().accept(player);
    }

    /**
     * @return The left click consumer.
     * @deprecated Deprecated in favor of {@link #getLeftConsumer()} 17th March 2020
     */
    @Deprecated
    protected Consumer<Player> getConsumer() {
        return getLeftConsumer();
    }

    protected Consumer<Player> getLeftConsumer() {
        return _left;
    }

    protected Consumer<Player> getRightConsumer() {
        return _right;
    }
}