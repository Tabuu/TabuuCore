package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.item.ItemList;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.util.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class ItemInput extends StyleableElement<Style> implements IClickable, IValuable<ItemStack> {
    private boolean _clone;
    private ItemStack _value;
    private BiConsumer<Player, ItemStack> _consumer;

    public ItemInput(Style style, boolean clone) {
        super(style);
        _clone = clone;
    }

    public ItemInput(Style style, boolean clone, BiConsumer<Player, ItemStack> consumer) {
        this(style, clone);
        _clone = clone;
        _consumer = consumer;
    }

    public BiConsumer<Player, ItemStack> getConsumer() {
        return _consumer;
    }

    @Override
    public void click(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = player.getItemOnCursor().clone();
        ItemStack value = getValue().clone();
        ItemStack swap = value.clone();

        if(_clone) {
            switch (event.getAction()) {
                case PLACE_ALL:
                case PICKUP_ALL:
                case SWAP_WITH_CURSOR:
                    break;

                default:
                    event.setCancelled(true);
                    return;
            }

            setValue(player, cursor.clone());
            player.setItemOnCursor(cursor.clone());
            return;
        }

        switch (event.getClick()) {
            case LEFT:
                if (value.isSimilar(cursor)) {
                    int total = value.getAmount() + cursor.getAmount();
                    if (total >= value.getMaxStackSize()) {
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
                if (BukkitUtils.isAir(cursor) && !BukkitUtils.isAir(value)) {
                    int half = (int) Math.ceil(swap.getAmount() / 2d);
                    cursor = swap.clone();
                    cursor.setAmount(half);
                    value.setAmount(value.getAmount() - half);
                } else if ((cursor.isSimilar(value)) && value.getAmount() < value.getMaxStackSize()) {
                    cursor.setAmount(cursor.getAmount() - 1);
                    value.setAmount(value.getAmount() + 1);
                } else if (BukkitUtils.isAir(value)) {
                    value = cursor.clone();
                    value.setAmount(1);
                    cursor.setAmount(cursor.getAmount() - 1);
                }
                break;

            case NUMBER_KEY:
                ItemStack hotbar = player.getInventory().getItem(event.getHotbarButton());
                if (hotbar == null) hotbar = XMaterial.AIR.parseItem();
                player.getInventory().setItem(event.getHotbarButton(), swap);
                assert hotbar != null;
                value = hotbar.clone();
                break;

            case SHIFT_LEFT:
            case SHIFT_RIGHT:
                ItemList inventory = new ItemList();
                inventory.addAll(BukkitUtils.getStorageContents(player));
                value = inventory.stack(event.getCurrentItem());
                if (value != null) inventory.remove(inventory.size() - 1);
                BukkitUtils.setStorageContents(player, inventory.toArray(new ItemStack[0]));
                break;

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

        player.setItemOnCursor(cursor.clone());

        if (value == null) value = XMaterial.AIR.parseItem();
        assert value != null;
        setValue(player, value.clone());
    }

    public boolean isCloaning() {
        return _clone;
    }

    @Override
    public ItemStack getValue() {
        return _value == null ? XMaterial.AIR.parseItem() : _value;
    }

    @Override
    public void setValue(ItemStack value) {
        _value = value;
    }

    public void setValue(Player player, ItemStack value) {
        setValue(value);
        if (getConsumer() != null) getConsumer().accept(player, value);
    }

    @Override
    public void updateStyle() {
        setDisplayItem(isEnabled() ? _value : getStyle().getDisabled());
    }
}