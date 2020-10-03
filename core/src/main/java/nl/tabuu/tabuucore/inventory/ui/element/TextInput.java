package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.TextInputUI;
import nl.tabuu.tabuucore.inventory.ui.element.style.TextInputStyle;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.BiConsumer;

public class TextInput extends StyleableElement<TextInputStyle> implements IClickable, IValuable<String> {

    private String _value;
    private InventoryUI _returnUI;
    private BiConsumer<Player, String> _consumer;

    public TextInput(TextInputStyle style, InventoryUI returnUI) {
        super(style);
        _returnUI = returnUI;
    }

    public TextInput(TextInputStyle style, InventoryUI returnUI, BiConsumer<Player, String> consumer) {
        this(style, returnUI);
        _consumer = consumer;
        _value = "";
    }

    public BiConsumer<Player, String> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        new TextInputUI(getStyle().getRenameItem(), getStyle().getPlaceHolder(), this::setValue, this::returnToUI).open(player);
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
            if(_consumer != null)
                _consumer.accept(player, value);
        }
        returnToUI(player);
    }

    private void returnToUI(Player player){
        getReturnUI().open(player);
    }

    public InventoryUI getReturnUI(){
        return _returnUI;
    }
}