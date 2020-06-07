package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.item.ItemBuilder;
import nl.tabuu.tabuucore.material.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class ItemInput extends StyleableElement<Style> implements IClickable, IValuable<ItemStack> {

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

    protected BiConsumer<Player, ItemStack> getConsumer() {
        return _consumer;
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = player.getItemOnCursor().clone();
        ItemStack value = getValue().clone();
        ItemStack swap = value.clone();

        switch (event.getClick()) {
            case LEFT:
                if(value.isSimilar(cursor)) {
                    int total = value.getAmount() + cursor.getAmount();
                    if(total >= value.getMaxStackSize()) {
                        value.setAmount(value.getMaxStackSize());
                        total -= value.getMaxStackSize();
                    } else {
                        value.setAmount(total);
                        total = 0;
                    }

                    cursor.setAmount(total);
                } else {
                    value = cursor;
                    cursor = swap;
                }
                break;

            case RIGHT:
                if(cursor.getType().isAir() && !value.getType().isAir()) {
                    int half = (int) Math.ceil(swap.getAmount() / 2d);
                    cursor = swap.clone();
                    cursor.setAmount(half);
                    value.setAmount(value.getAmount() - half);
                }
                else if((cursor.isSimilar(value)) && value.getAmount() < value.getMaxStackSize()) {
                    cursor.setAmount(cursor.getAmount() - 1);
                    value.setAmount(value.getAmount() + 1);
                }
                else if(value.getType().isAir()) {
                    value = cursor.clone();
                    value.setAmount(1);
                    cursor.setAmount(cursor.getAmount() - 1);
                }
                break;

            case NUMBER_KEY:
                ItemStack hotbar = player.getInventory().getItem(event.getHotbarButton());
                if(hotbar == null) hotbar = new ItemBuilder(XMaterial.AIR).build();
                player.getInventory().setItem(event.getHotbarButton(), swap);
                value = hotbar.clone();
                break;

            case SHIFT_LEFT:
            case SHIFT_RIGHT:
            case DOUBLE_CLICK:
            case DROP:
            case CONTROL_DROP:
            case CREATIVE:
            case UNKNOWN:
            case MIDDLE:
            case WINDOW_BORDER_LEFT:
            case WINDOW_BORDER_RIGHT:
                break;
        }

        if (!_clone) player.setItemOnCursor(cursor.clone());

        setValue(value.clone());

        if (getConsumer() != null) getConsumer().accept(player, cursor);
    }

    @Override
    public ItemStack getValue() {
        return _value;
    }

    @Override
    public void setValue(ItemStack value) {
        _value = value;
    }

    @Override
    public void updateStyle() {
        super.updateStyle();
        if (isEnabled()) setDisplayItem(_value);
    }
}
