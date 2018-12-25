package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.graphics.InventoryCanvas;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class InventoryUI extends InventoryCanvas {

    private Inventory _inventory;
    private String _title;
    private InventorySize _size;
    private List<InventoryAction> _blockedActions;
    private boolean _reloading;

    public InventoryUI(String title, InventorySize size){
        _title = title;
        _size = size;
        _blockedActions = new ArrayList<>();
        _reloading = false;
        createInventory();
        Bukkit.getScheduler().runTask(TabuuCore.getInstance(), this::draw);
    }

    public void open(HumanEntity player){
        player.openInventory(getInventory());
    }

    public void close(HumanEntity player){
        InventoryView view = player.getOpenInventory();
        Inventory topInventory = view.getTopInventory();

        if(topInventory != null && topInventory.equals(getInventory()))
            player.closeInventory();
    }

    public void closeAll(){
        getInventory().getViewers().forEach(this::close);
    }

    public void reload(){
        _reloading = true;
        List<HumanEntity> viewers = new ArrayList<>(getInventory().getViewers());

        ItemStack[] contents = Arrays.copyOfRange(getInventory().getContents().clone(), 0, _size.getSize());
        createInventory();
        getInventory().setContents(contents);

        viewers.forEach(this::open);
        _reloading = false;
    }

    public boolean isReloading(){
        return _reloading;
    }

    private void createInventory(){
        switch (_size){

            case HOPPER:
                _inventory = Bukkit.createInventory(null, InventoryType.HOPPER, _title);
                break;

            default:
                _inventory = Bukkit.createInventory(null, _size.getSize(), _title);
                break;

        }

        TabuuCore.getInstance().getInventoryUIManager().register(this);
    }

    public void setTile(String title){
        _title = title;
    }

    public void setSize(InventorySize size){
        _size = size;
    }

    public List<InventoryAction> getBlockedActions(){
        return _blockedActions;
    }

    public boolean isBlockedAction(InventoryAction action){
        return _blockedActions.contains(action);
    }

    public void addBlockedAction(InventoryAction... actions){
        _blockedActions.addAll(Arrays.asList(actions));
    }

    public void setBlockedActions(InventoryAction... actions){
        _blockedActions.clear();
        _blockedActions.addAll(Arrays.asList(actions));
    }

    public void removeBlockedAction(InventoryAction... actions){
        _blockedActions.removeAll(Arrays.asList(actions));
    }

    @Override
    public Inventory getInventory(){
        return _inventory;
    }

    @Override
    public void setInventory(Inventory inventory){
        _inventory = inventory;
    }

    @Override
    public void onClick(Player player, InventoryUIClick click){ }

    @Override
    public void onOpen(Player player){}

    @Override
    public void onClose(Player player){}
}