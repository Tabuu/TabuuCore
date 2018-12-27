package nl.tabuu.tabuucore.inventory.ui.element;

import nl.tabuu.tabuucore.inventory.ui.InventoryUIClick;
import org.bukkit.entity.Player;

public interface IClickable {
    void click(Player player, InventoryUIClick click);
}
