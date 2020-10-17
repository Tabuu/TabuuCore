package nl.tabuu.tabuucore.inventory.ui;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.inventory.InventorySize;
import nl.tabuu.tabuucore.inventory.ui.element.Element;
import nl.tabuu.tabuucore.inventory.ui.element.IClickable;
import nl.tabuu.tabuucore.inventory.ui.element.ItemInput;
import nl.tabuu.tabuucore.inventory.ui.element.StyleableElement;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Predicate;

public abstract class InventoryFormUI extends InventoryUI {

    private Map<Vector2f, Element> _elements;

    public InventoryFormUI(String title, InventorySize size) {
        super(title, size);
        _elements = new HashMap<>();
        addBlockedAction(InventoryAction.MOVE_TO_OTHER_INVENTORY, InventoryAction.COLLECT_TO_CURSOR);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (!event.getClick().equals(ClickType.SHIFT_LEFT) && !event.getClick().equals(ClickType.SHIFT_RIGHT)) return;

        if (event.getInventory().equals(event.getClickedInventory())) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        List<ItemInput> inputs = new LinkedList<>(getElements(ItemInput.class));
        inputs.sort(Comparator.comparing(Element::getPosition, ((instance, other) -> {
            if (instance.getY() > other.getY()) return 1;
            else if (instance.getY() < other.getY()) return -1;
            else return Float.compare(instance.getX(), other.getX());
        })));

        for (ItemInput input : inputs) {
            ItemStack value = input.getValue().clone();
            int available = value.getMaxStackSize() - value.getAmount();
            int amount = item.getAmount();

            if (value.isSimilar(item)) {
                if (available <= 0) continue;

                if (amount > available) {
                    value.setAmount(value.getMaxStackSize());
                    item.setAmount(item.getAmount() - available);
                } else {
                    value.setAmount(value.getAmount() + amount);
                    item.setAmount(0);
                }

                input.setValue(player, value.clone());
            } else if (value.getType().isAir()) {
                input.setValue(player, item.clone());
                item.setAmount(0);
            }

            if (item.getAmount() < 1) break;
        }
    }

    @Override
    public void onClickUI(InventoryClickEvent event) {
        event.setCancelled(true);

        Vector2f position = getSize().slotToVector(event.getSlot());
        Element element = _elements.get(position);

        if (element instanceof IClickable && element.isEnabled()) {
            IClickable clickable = (IClickable) element;
            clickable.click(event);
            updateElement(position);
        }
    }

    @Override
    public void onDragUI(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = event.getCursor();
        if (cursor == null) {
            cursor = event.getOldCursor().clone();
            cursor.setAmount(0);
        }

        int illegallyPlaced = 0;
        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            Vector2f slot;
            ItemStack item = entry.getValue();

            try {
                slot = getSize().slotToVector(entry.getKey());
            } catch (IllegalArgumentException exception) {
                continue;
            }

            Element element = _elements.get(slot);
            if (element instanceof ItemInput) {
                ItemInput input = (ItemInput) element;
                input.setValue(player, entry.getValue());
            } else {
                final ItemStack oldItem;

                if (event.getView().getItem(entry.getKey()) == null) oldItem = XMaterial.AIR.parseItem();
                else oldItem = event.getView().getItem(entry.getKey());
                assert oldItem != null;

                int delta = item.getAmount() - oldItem.getAmount();
                illegallyPlaced += delta;
                Bukkit.getScheduler().runTask(TabuuCore.getInstance(), () -> setItemAt(slot, oldItem));
            }
        }

        cursor.setAmount(cursor.getAmount() + illegallyPlaced);
        event.setCursor(cursor);
    }

    @Override
    protected void onDraw() {
        _elements.keySet().forEach(this::updateElement);
    }

    public void updateElement(Vector2f position) {
        Element element = _elements.get(position);

        if (element instanceof StyleableElement) {
            StyleableElement<?> styleable = (StyleableElement<?>) element;
            styleable.updateStyle();
            setItemAt(position, styleable.getDisplayItem());
        }
    }

    public void setElement(Vector2f position, Element element) {
        _elements.put(position, element);
        element.setPosition(position);
    }

    private Set<Element> getElements() {
        return getElements(Element.class);
    }

    private <T extends Element> Set<T> getElements(Class<T> type) {
        return getElements(type, null);
    }

    private <T extends Element> Set<T> getElements(Class<T> type, Predicate<T> filter) {
        Set<T> elements = new HashSet<>();

        for (Element element : _elements.values()) {
            if (!type.isInstance(element)) continue;
            T candidate = type.cast(element);

            if (filter != null && !filter.test(candidate)) continue;
            elements.add(type.cast(element));
        }

        return elements;
    }
}