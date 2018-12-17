package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.nms.wrapper.IAnvilUtil;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;

public class TextInputUI extends InventoryFormUI{

    private IAnvilUtil _anvilUtil;
    private int _containerId;
    private BiConsumer<Player, String> _onTextSubmit;
    private ItemStack _renameItem;
    private String _defaultValue;

    public TextInputUI(ItemStack renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit){
        super("", InventorySize.ONE_ROW);
        _onTextSubmit = onTextSubmit;
        _renameItem = renameItem;
        _anvilUtil = IAnvilUtil.get();
        _defaultValue = defaultValue;
    }

    @Override
    public void draw(){
        ItemStack renameItem = _renameItem;
        ItemMeta itemMeta = renameItem.getItemMeta();
        itemMeta.setDisplayName(_defaultValue);
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
        _onTextSubmit.accept(player, string);
        _anvilUtil.handleInventoryCloseEvent(player);
    }
}
