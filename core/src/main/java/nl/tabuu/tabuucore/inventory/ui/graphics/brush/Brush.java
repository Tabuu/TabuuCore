package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Brush implements IBrush{

    private ItemStack _itemStack;

    public Brush(ItemStack itemStack){
        _itemStack = itemStack;
    }

    public Brush(Material material){
        this(new ItemStack(material));
    }

    public ItemStack get(Vector2f position) {
        return _itemStack;
    }
}
