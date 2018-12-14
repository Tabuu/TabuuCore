package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.inventory.ui.graphics.InventoryCanvas;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CheckerBrush implements IBrush {

    protected ItemStack[] _items;

    public CheckerBrush(ItemStack... items) {
        _items = items;
    }

    public CheckerBrush(Material... items) {
        this(Arrays.stream(items).map(ItemStack::new).toArray(ItemStack[]::new));
    }

    @Override
    public ItemStack get(Vector2f position){
        int slot = InventoryCanvas.vectorToSlot(position);
        return _items[slot % _items.length];
    }
}
