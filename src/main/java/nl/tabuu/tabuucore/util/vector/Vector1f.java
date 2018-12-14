package nl.tabuu.tabuucore.util.vector;

public class Vector1f extends Vectorf{

    protected Vector1f(float... values){
        super(values);
    }

    public Vector1f(float x){
        super(x);
    }

    public float getX(){
        return getValues()[0];
    }

    public int getIntX(){
        return (int) Math.floor(getX());
    }

}
