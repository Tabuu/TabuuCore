package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.TextInput;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class TextInputUI extends InventoryFormUI{

    private TextInput _textInputElement;

    public TextInputUI(String title, TextInput textInputElement) {
        super(title, InventorySize.ONE_ROW);
        _inventory = Bukkit.createInventory(this, InventoryType.ANVIL, title);
        _textInputElement = textInputElement;
    }

    @Override
    protected void draw() {

        Style buttonStyle = new Style(Material.GREEN_WOOL, Material.GRAY_WOOL);
        Button confirmButton = new Button(buttonStyle);
        Button cancelButton = new Button(buttonStyle, this::returnToUI);

        setItemAt(new Vector2f(0, 0), new ItemStack(Material.NAME_TAG));
        addElement(new Vector2f(1,0), confirmButton);
        addElement(new Vector2f(2,0), cancelButton);

        AnvilInventory inventory = (AnvilInventory) getInventory();
        inventory.setItem(0, new ItemStack(Material.NAME_TAG));

        super.draw();
    }

    @Override
    public void onClose(Player player){
        returnToUI(player);
    }

    @Override
    public boolean onClick(Player player, InventoryUIClick click){
            Bukkit.broadcastMessage(click.getSlot() + "");

        return true;
    }

    private void onAccept(Player player){
        AnvilInventory inventory = (AnvilInventory) getInventory();
        _textInputElement.setValue(player, inventory.getRenameText());
        returnToUI(player);
    }

    private void returnToUI(Player player){
        player.openInventory(_textInputElement.returnInventory());
    }
}
