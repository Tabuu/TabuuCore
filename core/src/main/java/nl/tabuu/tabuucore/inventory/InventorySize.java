package nl.tabuu.tabuucore.inventory;

import nl.tabuu.tabuucore.util.vector.Vector2f;

public enum InventorySize {
    ONE_ROW(9, 1),
    TWO_ROWS(9, 2),
    THREE_ROWS(9, 3),
    FOUR_ROWS(9, 4),
    FIVE_ROWS(9, 5),
    SIX_ROWS(9, 6),
    THREE_BY_THREE(3, 3),
    ONE_BY_FIVE(1, 5),

    /**
     * @deprecated Deprecated in favor of {@link #THREE_ROWS}
     */
    @Deprecated
    SINGLE_CHEST(9, 3),

    /**
     * @deprecated Deprecated in favor of {@link #SIX_ROWS}
     */
    @Deprecated
    DOUBLE_CHEST(9, 6),

    /**
     * @deprecated Deprecated in favor of {@link #ONE_BY_FIVE}
     */
    @Deprecated
    HOPPER(1, 5);

    private int _width, _height;

    InventorySize(int width, int height){
        _width = width;
        _height = height;
    }

    public int getSize(){
        return getWidth() * getHeight();
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public Vector2f slotToVector(int slot) {
        if(slot >= getSize())
            throw new IllegalArgumentException("Slot may not exceed inventory size!");

        int x = slot % getWidth();
        int y =  Math.floorDiv(slot, getWidth());

        return new Vector2f(x, y);
    }

    public int vectorToSlot(Vector2f vector) {
        int slot = vector.getIntX() + (vector.getIntY() * getWidth());

        if(slot >= getSize())
            throw new IllegalArgumentException("Slot may not exceed inventory size!");

        return slot;
    }
}
