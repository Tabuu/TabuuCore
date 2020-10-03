package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class Button extends StyleableElement<Style> implements IClickable {

    private Consumer<Player> _onLeftClick, _onRightClick;

    public Button(Style style) {
        super(style);
    }

    public Button(Style style, Consumer<Player> onLeftClick, Consumer<Player> onRightClick) {
        this(style);
        _onLeftClick = onLeftClick;
        _onRightClick = onRightClick;
    }

    public Button(Style style, Consumer<Player> onClick) {
        this(style, onClick, onClick);
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.isLeftClick() && getLeftConsumer() != null)
            getLeftConsumer().accept(player);

        if (event.isRightClick() && getRightConsumer() != null)
            getRightConsumer().accept(player);
    }

    protected Consumer<Player> getLeftConsumer() {
        return _onLeftClick;
    }

    protected Consumer<Player> getRightConsumer() {
        return _onRightClick;
    }
}