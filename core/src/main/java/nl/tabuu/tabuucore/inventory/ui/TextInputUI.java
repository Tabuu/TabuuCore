package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.nms.wrapper.IInventoryUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;

public class TextInputUI extends InventoryFormUI{

    private IInventoryUtil _anvilUtil;
    private int _containerId;
    private IAnvilContainerWindow _window;
    private BiConsumer<Player, String> _onTextSubmit;
    private ItemStack _renameItem;
    private String _defaultValue;

    public TextInputUI(ItemStack renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit){
        super("", InventorySize.ONE_ROW);
        _onTextSubmit = onTextSubmit;
        _renameItem = renameItem;
        _anvilUtil = IInventoryUtil.get();
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

        setElement(new Vector2f(2, 0), submitButton);
        setElement(new Vector2f(0, 0), submitButton);
        super.draw();
    }

    @Override
    public void open(HumanEntity human){
        Player player = (Player) human;

        _window = IContainerWindow.get(player, IAnvilContainerWindow.class);

        setInventory(_window.getInventory());

        _window.open();

        TabuuCore.getInstance().getInventoryUIManager().register(this);
    }

    @Override
    public void onClose(Player player){
        _anvilUtil.setActiveContainerToDefault(player);
        _anvilUtil.sendPacketCloseWindow(player, _containerId);
    }

    private void submit(Player player){
        String string = _window.getRenameText();
        _onTextSubmit.accept(player, string);
        _anvilUtil.handleInventoryCloseEvent(player);
    }
}
