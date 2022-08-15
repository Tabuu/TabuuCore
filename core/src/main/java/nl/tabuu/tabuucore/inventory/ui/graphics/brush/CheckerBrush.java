package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.item.ItemBuilder;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CheckerBrush implements IBrush {

    protected ItemStack[] _items;

    public CheckerBrush(ItemStack... items) {
        _items = items;
    }

    public CheckerBrush(ItemBuilder... items) {
        this(Arrays.stream(items).map(ItemBuilder::build).toArray(ItemStack[]::new));
    }

    public CheckerBrush(Material... items) {
        this(Arrays.stream(items).map(ItemStack::new).toArray(ItemStack[]::new));
    }

    public CheckerBrush(XMaterial... items) {
        this(Arrays.stream(items).map(XMaterial::parseItem).toArray(ItemStack[]::new));
    }

    @Override
    public ItemStack get(Vector2f position) {
        int slot = InventorySize.SIX_ROWS.vectorToSlot(position);
        return _items[slot % _items.length];
    }
}