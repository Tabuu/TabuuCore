package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.debug.Debug;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.TextInputUI;
import nl.tabuu.tabuucore.inventory.ui.element.style.TextInputStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiConsumer;

public class TextInput extends StyleableElement<TextInputStyle> implements IClickable, IValuable<String> {

    private String _value;
    private final InventoryUI _returnUI;
    private final BiConsumer<Player, String> _consumer;

    /**
     * Creates a new TextInput element with a return ui and consumer.
     * @param style The element's style.
     * @param returnUI The ui the player will return to after setting the value. Can be set to null for no return.
     * @param consumer This consumer will be accepted with the set value and the setting player.
     */
    public TextInput(@Nonnull TextInputStyle style, @Nullable InventoryUI returnUI, @Nullable BiConsumer<Player, String> consumer) {
        super(style);

        _value = "";
        _returnUI = returnUI;
        _consumer = consumer;
    }

    public TextInput(TextInputStyle style, @Nullable InventoryUI returnUI) {
        this(
                style,
                returnUI,
                null
        );
    }

    public TextInput(TextInputStyle style) {
        this(
                style,
                null,
                null
        );
    }


    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        new TextInputUI(player, getStyle().getRenameItem(), getStyle().getPlaceHolder(), this::setValue, this::returnToUI).open(player);
    }

    private void returnToUI(Player player){
        InventoryUI ui = getReturnUI();
        if (Objects.nonNull(ui))
            ui.open(player);
    }

    @Override
    public String getValue() {
        return _value;
    }

    @Override
    public void setValue(String value) {
        _value = value;
    }

    public void setValue(Player player, String value){
        if(!value.equals(getStyle().getPlaceHolder())){
            setValue(value);

            BiConsumer<Player, String> consumer = getConsumer();
            if(consumer != null)
                consumer.accept(player, value);
        }
        returnToUI(player);
    }

    public BiConsumer<Player, String> getConsumer(){
        return _consumer;
    }

    public InventoryUI getReturnUI(){
        return _returnUI;
    }
}