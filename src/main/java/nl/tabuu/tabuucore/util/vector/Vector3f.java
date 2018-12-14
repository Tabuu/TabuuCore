package nl.tabuu.tabuucore.util.vector;

public class Vector3f extends Vector2f {

    public Vector3f(float... values){
        super(values);
    }

    public Vector3f(float x, float y, float z){
        super(x, y, z);
    }

    public float getZ(){
        return getValues()[2];
    }

    public int getIntZ(){
        return (int) Math.floor(getZ());
    }

}
