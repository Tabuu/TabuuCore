package nl.tabuu.tabuucore.debug;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.InventoryFormUI;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.Checkbox;
import nl.tabuu.tabuucore.inventory.ui.element.ItemInput;
import nl.tabuu.tabuucore.inventory.ui.element.TextInput;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.inventory.ui.element.style.TextInputStyle;
import nl.tabuu.tabuucore.inventory.ui.element.style.ToggleableStyle;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.CheckerBrush;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.IBrush;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TestUI extends InventoryFormUI {

    private TextInput _textInput;
    private ItemInput _itemInput;
    private Button _submitButton;

    public TestUI(){
        super("Item Editor", InventorySize.FOUR_ROWS);
    }

    @Override
    protected void draw() {
        IBrush brush = new CheckerBrush(Material.GOLD_INGOT, Material.DIAMOND);

        setBrush(brush);
        drawRectangle(new Vector2f(0, 0), new Vector2f(8, 3));

        Style itemInputStyle = new Style(Material.AIR, Material.IRON_INGOT);
        _itemInput = new ItemInput(itemInputStyle, false);

        Style buttonStyle = new Style(Material.GOLD_BLOCK, Material.STONE_SWORD);
        _submitButton = new Button(buttonStyle, this::onSubmitButtonClick);
        _submitButton.setEnabled(false);

        TextInputStyle textStyle = new TextInputStyle(Material.FEATHER, Material.STONE, Material.NAME_TAG, "Enter Text");
        _textInput = new TextInput(textStyle, this, this::onTextChange);

        ToggleableStyle style = new ToggleableStyle(Material.LIME_DYE, Material.GRAY_DYE, Material.IRON_BARS);
        Checkbox checkbox = new Checkbox(style);

        addElement(new Vector2f(4, 1), _itemInput);
        addElement(new Vector2f(2, 2), _submitButton);
        addElement(new Vector2f(6, 2), _textInput);
        addElement(new Vector2f(1, 1), checkbox);

        super.draw();
    }

    private void onSubmitButtonClick(Player player){
        ItemStack item = _itemInput.getValue();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', _textInput.getValue()));
        item.setItemMeta(itemMeta);

        updateElement(new Vector2f(4, 1));

        _submitButton.setEnabled(false);
        _submitButton.update(this);
    }

    private void onTextChange(Player player, String string){
        player.sendMessage(string);
        _submitButton.setEnabled(true);
        _submitButton.update(this);
    }


}
