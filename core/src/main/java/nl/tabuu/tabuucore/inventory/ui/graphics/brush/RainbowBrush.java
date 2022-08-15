package nl.tabuu.tabuucore.inventory.ui.graphics.brush;

import nl.tabuu.tabuucore.item.ItemBuilder;
import nl.tabuu.tabuucore.material.XMaterial;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RainbowBrush extends CheckerBrush {

    private int _counter;
    private Random _random;
    private boolean _useRandom;

    public RainbowBrush(boolean random, ItemStack... items) {
        super(items);
        _random = new Random();
        _useRandom = random;
    }

    public RainbowBrush(ItemStack... items) {
        this(false, items);
    }

    public RainbowBrush(boolean random, ItemBuilder... items) {
        super(items);
        _random = new Random();
        _useRandom = random;
    }

    public RainbowBrush(ItemBuilder... items) {
        this(false, items);
    }

    public RainbowBrush(boolean random, Material... items) {
        super(items);
        _random = new Random();
        _useRandom = random;
    }

    public RainbowBrush(Material... items) {
        this(false, items);
    }

    public RainbowBrush(boolean random, XMaterial... items) {
        super(items);
        _random = new Random();
        _useRandom = random;
    }

    public RainbowBrush(XMaterial... items) {
        this(false, items);
    }

    @Override
    public ItemStack get(Vector2f position) {

        ItemStack itemStack;

        if(_useRandom){
            itemStack = _items[_random.nextInt(_items.length)];
        }
        else {
            itemStack = _items[_counter % _items.length];
            _counter++;
        }

        return itemStack;
    }
}