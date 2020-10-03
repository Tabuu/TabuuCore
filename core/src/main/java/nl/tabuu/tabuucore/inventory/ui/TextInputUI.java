package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.nms.wrapper.IInventoryUtil;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TextInputUI extends InventoryFormUI {
    private IInventoryUtil _anvilUtil;
    private IAnvilContainerWindow _anvilWindow;
    private BiConsumer<Player, String> _onTextSubmit;
    private Consumer<Player> _onClose;
    private ItemStack _renameItem;
    private String _defaultValue;

    public TextInputUI(ItemStack renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit, Consumer<Player> onClose) {
        super("", InventorySize.ONE_ROW);
        _onTextSubmit = onTextSubmit;
        _renameItem = renameItem;
        _anvilUtil = IInventoryUtil.get();
        _defaultValue = defaultValue;
        _onClose = onClose;
    }

    @Override
    public void onDraw() {
        ItemStack renameItem = _renameItem;
        ItemMeta itemMeta = renameItem.getItemMeta();
        itemMeta.setDisplayName(_defaultValue);

        renameItem.setItemMeta(itemMeta);

        Style submitButtonStyle = new Style(renameItem, new ItemStack(Material.AIR));
        Button submitButton = new Button(submitButtonStyle, this::submit);

        setElement(new Vector2f(2, 0), submitButton);
        setElement(new Vector2f(0, 0), submitButton);
        super.onDraw();
    }

    @Override
    public void open(HumanEntity human) {
        Player player = (Player) human;

        _anvilWindow = IContainerWindow.get(player, IAnvilContainerWindow.class);

        setInventory(_anvilWindow.getInventory());

        TabuuCore.getInstance().getInventoryUIManager().register(this);

        this.onDraw();
        _anvilWindow.open();
    }

    @Override
    public void onClose(Player player) {
        _anvilUtil.setActiveContainerToDefault(player);
        _anvilUtil.sendPacketCloseWindow(player, _anvilWindow.getWindowId());

        if(_onClose != null)
            Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> _onClose.accept(player));
    }

    @Override
    protected void createInventory() {
    }

    private void submit(Player player) {
        String string = _anvilWindow.getRenameText();
        _anvilUtil.handleInventoryCloseEvent(player);
        _onTextSubmit.accept(player, string);
    }
}