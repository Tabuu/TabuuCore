package nl.tabuu.tabuucore.inventory;

public enum InventorySize {

    ONE_ROW(9),
    TWO_ROWS(18),
    THREE_ROWS(27),
    FOUR_ROWS(36),
    FIVE_ROWS(45),
    SIX_ROWS(54),
    SINGLE_CHEST(27),
    DOUBLE_CHEST(54);

    private int _size;

    InventorySize(int size){
        _size = size;
    }

    public int getSize(){
        return _size;
    }
}
