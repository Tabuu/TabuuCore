package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIClick;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.inventory.ui.InventoryUIDrag;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class InventoryListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getClickedInventory() != null && event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            Inventory topInventory = event.getView().getTopInventory();
            InventoryUI inventoryUI = getUI(topInventory);

            if(inventoryUI != null){
                if(inventoryUI.isBlockedAction(event.getAction())){
                    event.setCancelled(true);
                    return;
                }

                InventoryUIClick click = new InventoryUIClick(event);
                inventoryUI.onClick(player, click);

                if(event.getClickedInventory().equals(topInventory)){
                    inventoryUI.onClickUI(player, click);
                }
                event.setCancelled(click.isCanceled());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if(event.getInventory() != null && event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            Inventory topInventory = event.getView().getTopInventory();
            InventoryUI inventoryUI = getUI(topInventory);

            if(inventoryUI != null){
                InventoryUIDrag drag = new InventoryUIDrag(event);
                inventoryUI.onDrag(player, drag);

                int highestSlot = Collections.max(event.getRawSlots());
                int lowestSlot = Collections.min(event.getRawSlots());

                if(highestSlot < topInventory.getSize() || lowestSlot < topInventory.getSize())
                    inventoryUI.onDragUI(player, drag);

                event.setCancelled(drag.isCanceled());
            }
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

    private InventoryUI getUI(Inventory inventory){
        return TabuuCore.getInstance().getInventoryUIManager().get(inventory);
    }

}
