package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.TextInputUI;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.function.BiConsumer;

public class TextInput extends StylableElement<Style> implements IClickable, IValuable<String>{

    //FixME: Fix it
    private String _value;
    private BiConsumer<Player, String> _consumer;
    private Inventory _returnInventory;

    public TextInput(Style style, Inventory inventory) {
        this(style, inventory, null);
    }

    public TextInput(Style style, Inventory returnInventory, BiConsumer<Player, String> consumer) {
        super(style);
        _value = "";
        _consumer = consumer;
        _returnInventory = returnInventory;
    }

    public BiConsumer<Player, String> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(Player player) {
        new TextInputUI("TextInput", this).open(player);
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
        setValue(value);
        if(_consumer != null)
            _consumer.accept(player, value);
    }

    public Inventory returnInventory(){
        return _returnInventory;
    }
}
