package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.TextInput;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.nms.NMSUtil;
import nl.tabuu.tabuucore.nms.wrapper.IAnvilUtil;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TextInputUI extends InventoryFormUI{

    private IAnvilUtil _anvilUtil;
    private TextInput _textInputElement;
    private int _containerId;

    public TextInputUI(String title, TextInput textInputElement) {
        super(title, InventorySize.DOUBLE_CHEST);
        _textInputElement = textInputElement;
        _anvilUtil = IAnvilUtil.getAnvilUtil();
    }

    private void returnToUI(Player player){
        player.openInventory(_textInputElement.returnInventory());
    }

    @Override
    public void draw(){
        ItemStack renameItem = _textInputElement.getStyle().getRenameItem().clone();
        ItemMeta itemMeta = renameItem.getItemMeta();
        itemMeta.setDisplayName(_textInputElement.getValue());
        renameItem.setItemMeta(itemMeta);

        Style submitButtonStyle = new Style(renameItem, new ItemStack(Material.AIR));
        Button submitButton = new Button(submitButtonStyle, this::submit);

        addElement(new Vector2f(2, 0), submitButton);
        addElement(new Vector2f(0, 0), submitButton);
        super.draw();
    }

    @Override
    public void open(HumanEntity human){
        Player player = (Player) human;

        _anvilUtil.handleInventoryCloseEvent(player);
        _anvilUtil.setActiveContainerDefault(player);

        Object container = _anvilUtil.newContainerAnvil(player);
        _inventory = _anvilUtil.toBukkitInventory(container);

        _containerId = _anvilUtil.getNextContainerId(player);
        _anvilUtil.sendPacketOpenWindow(player, _containerId);
        _anvilUtil.setActiveContainer(player, container);
        _anvilUtil.setActiveContainerId(container, _containerId);
        _anvilUtil.addActiveContainerSlotListener(container, player);

        TabuuCore.getInstance().getInventoryUIManager().register(this);
    }

    @Override
    public void onClose(Player player){
        _anvilUtil.setActiveContainerDefault(player);
        _anvilUtil.sendPacketCloseWindow(player, _containerId);
    }

    private void submit(Player player){
        String string = ((AnvilInventory) _inventory).getRenameText();
        _textInputElement.setValue(player, string);
        returnToUI(player);
    }
}
