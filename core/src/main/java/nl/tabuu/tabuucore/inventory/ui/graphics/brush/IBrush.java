package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.inventory.ItemStack;

public interface IBrush {

    ItemStack get(Vector2f position);

}
