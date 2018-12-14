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
            Inventory uiInventory = event.getView().getTopInventory();

            if(isUI(uiInventory)){
                InventoryUI ui = (InventoryUI) uiInventory.getHolder();
                if(ui.isBlockedAction(event.getAction())){
                    event.setCancelled(true);
                }
                else if(event.getClickedInventory() != null && event.getClickedInventory().equals(uiInventory)){
                    InventoryUIClick click = new InventoryUIClick(event);
                    event.setCancelled(ui.onClick(player, click));
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerItemDrop(PlayerDropItemEvent event){
        Player player  = event.getPlayer();
        InventoryView view = player.getOpenInventory();

        if(isUI(view.getTopInventory()) && !player.getItemOnCursor().getType().equals(Material.AIR)){
            ItemStack itemStack = event.getItemDrop().getItemStack().clone();
            event.getItemDrop().remove();
            Bukkit.getScheduler().runTaskLater(TabuuCore.getInstance(), () -> player.setItemOnCursor(itemStack), 1L);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        if(inventory.equals(event.getView().getTopInventory()) && isUI(inventory)){
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if(isUI(inventory)){
            InventoryUI inventoryUI = (InventoryUI) inventory.getHolder();
            if(!inventoryUI.isReloading())
                inventoryUI.onOpen(player);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event){
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        if(isUI(inventory)){
            InventoryUI inventoryUI = (InventoryUI) inventory.getHolder();
            if(!inventoryUI.isReloading())
                inventoryUI.onClose(player);
        }
    }

    private boolean isUI(Inventory inventory){
        return inventory != null &&
                inventory.getHolder() != null &&
                inventory.getHolder() instanceof IInventoryUI;
    }


}
