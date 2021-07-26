package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.item.ItemBuilder;
import com.cryptomorin.xseries.XMaterial;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Brush implements IBrush {

    private ItemStack _itemStack;

    public Brush(ItemStack itemStack) {
        _itemStack = itemStack;
    }

    public Brush(ItemBuilder builder) {
        this(builder.build());
    }

    public Brush(Material material) {
        this(new ItemStack(material));
    }

    public Brush(XMaterial material) {
        this(material.parseItem());
    }

    public ItemStack get(Vector2f position) {
        return _itemStack;
    }
}
