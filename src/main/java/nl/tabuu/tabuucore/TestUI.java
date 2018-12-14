package nl.tabuu.tabuucore;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.InventoryFormUI;
import nl.tabuu.tabuucore.inventory.ui.element.Checkbox;
import nl.tabuu.tabuucore.inventory.ui.element.ItemInput;
import nl.tabuu.tabuucore.inventory.ui.element.TextInput;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.inventory.ui.element.style.ToggleableStyle;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.CheckerBrush;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.IBrush;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestUI extends InventoryFormUI {

    private ItemInput _itemInput;

    public TestUI(){
        super("Test Title", InventorySize.DOUBLE_CHEST);
    }

    @Override
    protected void draw() {
        IBrush brush = new CheckerBrush(Material.WHITE_STAINED_GLASS_PANE, Material.BLACK_STAINED_GLASS_PANE);

        setBrush(brush);
        drawRectangle(new Vector2f(0, 0), new Vector2f(8, 5));

        Style style = new Style(Material.AIR, Material.IRON_BARS);
        _itemInput = new ItemInput(style, false, this::onItemChange);

        ToggleableStyle toggleableStyle = new ToggleableStyle(Material.LIME_DYE, Material.GRAY_DYE, Material.IRON_BARS);
        Checkbox checkbox = new Checkbox(toggleableStyle);

        Style textStyle = new Style(Material.MAP, Material.FILLED_MAP);
        TextInput textInput = new TextInput(textStyle, this.getInventory());

        addElement(new Vector2f(2, 1), checkbox);
        addElement(new Vector2f(1, 1), _itemInput);
        addElement(new Vector2f(3, 1), textInput);

        super.draw();
    }

    private void onItemChange(Player player, ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", 200, AttributeModifier.Operation.ADD_NUMBER));
        itemStack.setItemMeta(itemMeta);
    }
}
