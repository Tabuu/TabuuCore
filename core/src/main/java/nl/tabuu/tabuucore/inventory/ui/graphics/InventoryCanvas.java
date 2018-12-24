package nl.tabuu.tabuucore.inventory.ui.graphics;

import nl.tabuu.tabuucore.inventory.ui.IInventoryUI;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.Brush;
import nl.tabuu.tabuucore.inventory.ui.graphics.brush.IBrush;
import nl.tabuu.tabuucore.util.vector.Vector2f;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class InventoryCanvas implements IInventoryUI {

    private IBrush _currentBrush;

    protected InventoryCanvas(){
        _currentBrush = new Brush(Material.AIR);
    }

    protected abstract void draw();

    protected void setBrush(IBrush brush){
        _currentBrush = brush;
    }

    protected void drawLine(Vector2f from, Vector2f to) {
        if(from.length() > to.length()){
            Vector2f t = new Vector2f(0, 0).copy(to);
            to = from;
            from = t;
        }

        Vector2f delta = to.subtract(from);

        double deltaError = Math.abs(delta.getY() / delta.getX());
        double error = 0;

        int y = from.getIntY();
        for(int x = from.getIntX(); x <= to.getIntX(); x++){

            Vector2f position = new Vector2f(x, y);
            drawPoint(position);

            error = error + deltaError;
            if(error > 0.5) {
                y += Math.signum(delta.getY()) * 1;
                error -= 1;
            }
        }
    }

    protected void drawRectangle(Vector2f from, Vector2f to){
        for(int i = from.getIntX(); i <= to.getIntX(); i++){
            Vector2f pos1 = new Vector2f(i, from.getIntY());
            Vector2f pos2 = new Vector2f(i, to.getIntY());
            drawPoint(pos1);
            drawPoint(pos2);
        }

        for(int i = from.getIntY(); i <= to.getIntY(); i++){
            Vector2f pos1 = new Vector2f(from.getIntX(), i);
            Vector2f pos2 = new Vector2f(to.getIntX(), i);
            drawPoint(pos1);
            drawPoint(pos2);
        }
    }

    protected void drawFilledRectangle(Vector2f from, Vector2f to){
        for(int y = from.getIntY(); y <= to.getIntY(); y++){
            for(int x = from.getIntX(); x <= to.getIntX(); x++){
                drawPoint(new Vector2f(x, y));
            }
        }
    }

    protected void drawPoint(Vector2f position){
        setItemAt(position, _currentBrush);
    }

    protected void setItemAt(Vector2f vector, ItemStack item){
        Inventory inventory = getInventory();
        inventory.setItem(vectorToSlot(vector), item);
    }

    protected void setItemAt(Vector2f position, IBrush brush){
        setItemAt(position, brush.get(position));
    }

    public static int vectorToSlot(Vector2f vector){
        return (vector.getIntY() * 9) + vector.getIntX();
    }

    public static Vector2f vectorToSlot(int slot){
        return new Vector2f(slot % 9, (float) Math.floor(slot / 9f));
    }
}
