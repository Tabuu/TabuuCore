package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class ItemInput extends StylableElement<Style> implements IClickable, IValuable<ItemStack> {

    private ItemStack _value;
    private boolean _clone;
    private BiConsumer<Player, ItemStack> _consumer;

    public ItemInput(Style style, boolean clone) {
        this(style, clone, null);
    }

    public ItemInput(Style style, boolean clone, BiConsumer<Player, ItemStack> consumer) {
        super(style);
        _clone = clone;
        _value = new ItemStack(Material.AIR);
        _consumer = consumer;
    }

    public BiConsumer<Player, ItemStack> getConsumer(){
        return _consumer;
    }

    @Override
    public void click(Player player) {
        ItemStack newValue = player.getItemOnCursor().clone();

        if(!_clone)
            player.setItemOnCursor(_value.clone());

        setValue(newValue);
        setDisplayItem(newValue);
        updateStyle();

        if(_consumer != null)
            _consumer.accept(player, newValue);
    }

    @Override
    public ItemStack getValue() {
        return _value;
    }

    @Override
    public void setValue(ItemStack value) {
        _value = value;
    }
}
