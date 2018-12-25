package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.inventory.ItemStack;

public interface INBTTagCompound {

    ItemStack apply(ItemStack item);

    INBTTagCompound copy(ItemStack item);

    boolean hasKey(String key);

    void removeKey(String key);

    // Setters
    void setBoolean(String key, boolean value);

    void setByte(String key, byte value);

    void setByteArray(String key, byte[] value);

    void setDouble(String key, double value);

    void setFloat(String key, float value);

    void setInt(String key, int value);

    void setIntArray(String key, int[] value);

    void setLong(String key, long value);

    void setShort(String key, short value);

    void setString(String key, String value);

    default void set(String key, Object value){
        if(value instanceof Boolean)
            setBoolean(key, (boolean) value);
        else if(value instanceof Byte)
            setByte(key, (byte) value);
        else if(value instanceof Byte[])
            setByteArray(key, (byte[]) value);
        else if(value instanceof Double)
            setDouble(key, (double) value);
        else if(value instanceof Float)
            setFloat(key, (float) value);
        else if(value instanceof Integer)
            setInt(key, (int) value);
        else if(value instanceof Integer[])
            setIntArray(key, (int[]) value);
        else if(value instanceof Long)
            setLong(key, (long) value);
        else if(value instanceof Short)
            setShort(key, (short) value);
        else if(value instanceof String)
            setString(key, (String) value);
    }

    // Getters
    boolean getBoolean(String key);

    byte getByte(String key);

    byte[] getByteArray(String key);

    double getDouble(String key);

    float getFloat(String key);

    int getInt(String key);

    int[] getIntArray(String key);

    long getLong(String key);

    short getShort(String key);

    String getString(String key);

    static INBTTagCompound get(){
        try {
            return (INBTTagCompound) NMSUtil.getWrapperClass("NBTTagCompound").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }

    static INBTTagCompound get(ItemStack item){
        INBTTagCompound tagCompound = get();
        tagCompound.copy(item);

        return tagCompound;
    }
}
