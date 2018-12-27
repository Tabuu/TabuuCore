package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.TextInputUI;
import nl.tabuu.tabuucore.inventory.ui.element.style.TextInputStyle;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class TextInput extends StylableElement<TextInputStyle> implements IClickable, IValuable<String>{

    private String _value;
    private BiConsumer<Player, String> _consumer;
    private InventoryUI _returnUI;

    public TextInput(TextInputStyle style, InventoryUI returnUI) {
        this(style, returnUI, null);
    }

    public TextInput(TextInputStyle style, InventoryUI returnUI, BiConsumer<Player, String> consumer) {
        super(style);
        _value = style.getPlaceHolder();
        _consumer = consumer;
        _returnUI = returnUI;
    }

    public BiConsumer<Player, String> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(Player player, InventoryUIClick click) {
        new TextInputUI(getStyle().getRenameItem(), getStyle().getPlaceHolder(), this::setValue).open(player);
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
