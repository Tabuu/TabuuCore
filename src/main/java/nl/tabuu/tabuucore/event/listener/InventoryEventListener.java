package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.IInventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryEventListener implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getView().getTopInventory();
            InventoryUI inventoryUI = getUI(inventory);

            if(inventoryUI != null){
                if(inventoryUI.isBlockedAction(event.getAction())){
                    event.setCancelled(true);
                }
                else if(event.getClickedInventory() != null && event.getClickedInventory().equals(inventory)){
                    InventoryUIClick click = new InventoryUIClick(event);
                    event.setCancelled(inventoryUI.onClick(player, click));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerItemDrop(PlayerDropItemEvent event){
        Player player  = event.getPlayer();
        InventoryView view = player.getOpenInventory();
        InventoryUI inventoryUI = getUI(view.getTopInventory());

        if(inventoryUI != null && !player.getItemOnCursor().getType().equals(Material.AIR)){
            ItemStack itemStack = event.getItemDrop().getItemStack().clone();
            event.getItemDrop().remove();
            Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> player.setItemOnCursor(itemStack));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        InventoryUI inventoryUI = getUI(inventory);

        if(inventory.equals(event.getView().getTopInventory())&& inventoryUI != null){
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        InventoryUI inventoryUI = getUI(inventory);

        if(inventoryUI != null){
            if(!inventoryUI.isReloading())
                inventoryUI.onOpen(player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        InventoryUI inventoryUI = getUI(inventory);

        if(inventoryUI != null){
            if(!inventoryUI.isReloading())
                inventoryUI.onClose(player);
        }
    }

    private InventoryUI getUI(Inventory inventory){
        return TabuuCore.getInstance().getInventoryUIManager().get(inventory);
    }

}
