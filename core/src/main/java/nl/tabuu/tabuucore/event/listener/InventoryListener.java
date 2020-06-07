package nl.tabuu.tabuucore.event.listener;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.ui.InventoryUI;
import nl.tabuu.tabuucore.item.ItemBuilder;
import nl.tabuu.tabuucore.util.vector.Vector2f;
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
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        Inventory topInventory = event.getView().getTopInventory();
        InventoryUI inventoryUI = getUI(topInventory);
        if (inventoryUI == null) return;

        if (inventoryUI.isBlockedAction(event.getAction())) {
            event.setCancelled(true);
            return;
        }
        else if (inventory.equals(topInventory))
            inventoryUI.onClickUI(event);

        inventoryUI.onClick(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Inventory topInventory = event.getView().getTopInventory();
        InventoryUI inventoryUI = getUI(topInventory);

        if (inventoryUI == null) return;
        inventoryUI.onDrag(event);

        int highestSlot = Collections.max(event.getRawSlots());
        int lowestSlot = Collections.min(event.getRawSlots());

        if (highestSlot < topInventory.getSize() || lowestSlot < topInventory.getSize())
            inventoryUI.onDragUI(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();

        Inventory inventory = event.getInventory();
        InventoryUI inventoryUI = getUI(inventory);
        if (inventoryUI != null) inventoryUI.onOpen(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();

        Inventory inventory = event.getInventory();
        InventoryUI inventoryUI = getUI(inventory);
        if (inventoryUI != null) inventoryUI.onClose(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        InventoryView view = player.getOpenInventory();
        InventoryUI inventoryUI = getUI(view.getTopInventory());

        if (inventoryUI == null) return;
        if (player.getItemOnCursor().getType().equals(Material.AIR)) return;

        ItemStack itemStack = event.getItemDrop().getItemStack().clone();
        event.getItemDrop().remove();
        Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> player.setItemOnCursor(itemStack));
    }

    private InventoryUI getUI(Inventory inventory) {
        return TabuuCore.getInstance().getInventoryUIManager().get(inventory);
    }
}
