package nl.tabuu.tabuucore.util.vector;

public class Vector2f extends Vector1f {

    protected Vector2f(float... values){
        super(values);
    }

    public Vector2f(float x, float y) {
        super(x, y);
    }

    public float getY(){
        return getValues()[1];
    }

    public int getIntY(){
        return (int) Math.floor(getY());
    }
}
