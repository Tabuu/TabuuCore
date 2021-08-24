package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Button;
import nl.tabuu.tabuucore.inventory.ui.element.style.Style;
import nl.tabuu.tabuucore.item.ItemBuilder;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.nms.wrapper.container.IAnvilContainerWindow;
import nl.tabuu.tabuucore.nms.wrapper.container.IContainerWindow;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TextInputUI extends InventoryFormUI {
    private IAnvilContainerWindow _anvilWindow;
    private final BiConsumer<Player, String> _onTextSubmit;
    private final Consumer<Player> _onClose;
    private final ItemStack _renameItem;
    private final String _defaultValue;
    private final Player _player;

    public TextInputUI(Player player, ItemStack renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit, Consumer<Player> onClose) {
        super("", InventorySize.ONE_ROW);
        _player = player;
        _onTextSubmit = onTextSubmit;
        _renameItem = renameItem;
        _defaultValue = defaultValue;
        _onClose = onClose;
    }

    public TextInputUI(Player player, ItemStack renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit) {
        this(player, renameItem, defaultValue, onTextSubmit, null);
    }

    public TextInputUI(Player player, ItemBuilder renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit, Consumer<Player> onClose) {
        this(player, renameItem.build(), defaultValue, onTextSubmit, onClose);
    }

    public TextInputUI(Player player, Material renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit, Consumer<Player> onClose) {
        this(player, new ItemStack(renameItem), defaultValue, onTextSubmit, onClose);
    }

    public TextInputUI(Player player, XMaterial renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit, Consumer<Player> onClose) {
        this(player, renameItem.parseItem(), defaultValue, onTextSubmit, onClose);
    }

    public TextInputUI(Player player, ItemBuilder renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit) {
        this(player, renameItem.build(), defaultValue, onTextSubmit);
    }

    public TextInputUI(Player player, Material renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit) {
        this(player, new ItemStack(renameItem), defaultValue, onTextSubmit);
    }

    public TextInputUI(Player player, XMaterial renameItem, String defaultValue, BiConsumer<Player, String> onTextSubmit) {
        this(player, renameItem.parseItem(), defaultValue, onTextSubmit);
    }

    @Override
    public void onDraw() {
        ItemStack renameItem = _renameItem;
        ItemMeta itemMeta = renameItem.getItemMeta();
        assert itemMeta != null : "The rename item must have item meta that can be edited.";

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
        if(human != getPlayer())
            throw new IllegalArgumentException("Can only open inventory for intended player.");

        initialize();
        _anvilWindow.open();
    }

    @Override
    public void close(HumanEntity human) {
        if(human != getPlayer())
            throw new IllegalArgumentException("Can only close inventory for intended player.");

        _anvilWindow.close();
    }

    @Override
    public void onClose(Player player) {
        if(_onClose != null)
            Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> _onClose.accept(player));

        getInventory().clear();
    }

    @Override
    protected Inventory createInventory() {
        _anvilWindow = IContainerWindow.get(getPlayer(), IAnvilContainerWindow.class);
        return _anvilWindow.getInventory();
    }

    public Player getPlayer() {
        return _player;
    }

    private void submit(Player player) {
        _onTextSubmit.accept(player, _anvilWindow.getRenameText());
        close(player);
    }
}