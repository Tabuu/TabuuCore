package nl.tabuu.tabuucore.nms.wrapper;

import nl.tabuu.tabuucore.nms.NMSUtil;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Represents a NMS NBTTagCompound which stores NBT data.
 */
public interface INBTTagCompound {

    /**
     * Returns a copy of the given {@link ItemStack} with the NBTTagCompound applied.
     * @param item the {@link ItemStack} the NBTTagCompound will be applied to.
     * @return a copy of the given {@link ItemStack} with the NBTTagCompound applied.
     */
    ItemStack apply(ItemStack item);

    /**
     * Returns an {@link Entity} with the NBTTagCompound applied.
     * @param entity the {@link Entity} the NBTTagCompound will be applied to.
     * @return an {@link Entity} with the NBTTagCompound applied.
     */
    Entity apply(Entity entity);

    /**
     * Copies the NBTTagCompound from the given {@link ItemStack} to this NBTTagCompound and returns itself.
     * @param item the {@link ItemStack} the NBTTagCompound will be copied from.
     * @return itself.
     */
    INBTTagCompound copy(ItemStack item);

    /**
     * Copies the NBTTagCompound from the given {@link Entity} to this NBTTagCompound and returns itself.
     * @param entity the {@link Entity} the NBTTagCompound will be copied from.
     * @return itself.
     */
    INBTTagCompound copy(Entity entity);

    /**
     * Copies the NBTTagCompound from the given bytes to this NBTTagCompound and returns itself.
     * @param bytes the bytes of the NBTTagCompound.
     * @return itself.
     */
    INBTTagCompound copy(byte[] bytes);

    /**
     * Returns whether or not a value has been set for the given key.
     * @param key key to check for value.
     * @return whether or not a value has been set for the given key.
     */
    boolean hasKey(String key);

    /**
     * Removes the given key and its value.
     * @param key Key to remove.
     */
    void removeKey(String key);

    // Setters

    /**
     * Sets the specified key to a new INBTTagCompound.
     * @param key key of the new INBTTagCompound.
     * @return the new INBTagCompound.
     */
    INBTTagCompound setTagCompound(String key);

    /**
     * Sets the specified key to the specified INBTTagCompound.
     * @param key key of the INBTTagCompound to set.
     * @param value new INBTTagCompound to set the key to.
     */
    void setTagCompound(String key, INBTTagCompound value);

    /**
     * Sets the specified key to the specified boolean.
     * @param key key of the boolean to set.
     * @param value new boolean to set the key to.
     */
    void setBoolean(String key, boolean value);

    /**
     * Sets the specified key to the specified byte.
     * @param key key of the byte to set.
     * @param value new byte to set the key to.
     */
    void setByte(String key, byte value);

    /**
     * Sets the specified key to the specified bytes.
     * @param key key of the bytes to set.
     * @param value new bytes to set the key to.
     */
    void setByteArray(String key, byte[] value);

    /**
     * Sets the specified key to the specified double.
     * @param key key of the double to set.
     * @param value new double to set the key to.
     */
    void setDouble(String key, double value);

    /**
     * Sets the specified key to the specified float.
     * @param key key of the float to set.
     * @param value new float to set the key to.
     */
    void setFloat(String key, float value);

    /**
     * Sets the specified key to the specified integer.
     * @param key key of the integer to set.
     * @param value new integer to set the key to.
     */
    void setInt(String key, int value);

    /**
     * Sets the specified key to the specified integers.
     * @param key key of the integers to set.
     * @param value new integers to set the key to.
     */
    void setIntArray(String key, int[] value);

    /**
     * Sets the specified key to the specified long.
     * @param key key of the long to set.
     * @param value new long to set the key to.
     */
    void setLong(String key, long value);

    /**
     * Sets the specified key to the specified short.
     * @param key key of the short to set.
     * @param value new short to set the key to.
     */
    void setShort(String key, short value);

    /**
     * Sets the specified key to the specified string.
     * @param key key of the string to set.
     * @param value new string to set the key to.
     */
    void setString(String key, String value);

    /**
     * Sets the specified key to the specified value.
     * @param key key of the value to set.
     * @param value new value to set the key to.
     */
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
        else if(value instanceof INBTTagCompound)
            setTagCompound(key, (INBTTagCompound) value);
    }

    // Getters

    /**
     * Returns the boolean associated with the given key.
     * @param key key of the boolean to return.
     * @return the boolean associated with the given key.
     */
    boolean getBoolean(String key);

    /**
     * Returns the byte associated with the given key.
     * @param key key of the byte to return.
     * @return the byte associated with the given key.
     */
    byte getByte(String key);

    /**
     * Returns the bytes associated with the given key.
     * @param key key of the bytes to return.
     * @return the bytes associated with the given key.
     */
    byte[] getByteArray(String key);

    /**
     * Returns the double associated with the given key.
     * @param key key of the double to return.
     * @return the double associated with the given key.
     */
    double getDouble(String key);

    /**
     * Returns the float associated with the given key.
     * @param key key of the float to return.
     * @return the float associated with the given key.
     */
    float getFloat(String key);

    /**
     * Returns the integer associated with the given key.
     * @param key key of the integer to return.
     * @return the integer associated with the given key.
     */
    int getInt(String key);

    /**
     * Returns the integers associated with the given key.
     * @param key key of the integers to return.
     * @return the integers associated with the given key.
     */
    int[] getIntArray(String key);

    /**
     * Returns the long associated with the given key.
     * @param key key of the long to return.
     * @return the long associated with the given key.
     */
    long getLong(String key);

    /**
     * Returns the short associated with the given key.
     * @param key key of the short to return.
     * @return the short associated with the given key.
     */
    short getShort(String key);

    /**
     * Returns the string associated with the given key.
     * @param key key of the string to return.
     * @return the string associated with the given key.
     */
    String getString(String key);

    /**
     * Returns the {@link Object#toString()} of the value associated with the given key.
     * @param key key of the value to return the string {@link Object#toString()} of.
     * @return the {@link Object#toString()} of the value associated with the given key.
     */
    String getObjectToString(String key);

    /**
     * Returns the {@link INBTTagCompound} of the value associated with the given key.
     * @param key key of the value to return the string {@link INBTTagCompound} of.
     * @return the {@link INBTTagCompound} of the value associated with the given key.
     */
    INBTTagCompound getTagCompound(String key);

    /**
     * Returns all keys stored in this NBTTagCompound.
     * @return all keys stored in this NBTTagCompound.
     */
    Set<String> getKeys();

    /**
     * Converts this NBTTagCompound to bytes.
     * @return this NBTTagCompound converted to bytes.
     * @see #get(byte[])
     */
    byte[] toByteArray();

    /**
     * Returns a new empty NBTTagCompound.
     * @return a new empty NBTTagCompound.
     */
    static INBTTagCompound get(){
        try {
            return (INBTTagCompound) NMSUtil.getWrapperClass("NBTTagCompound").getConstructor().newInstance();
        }
        catch (Exception ignored) {}

        return null;
    }

    /**
     * Returns a new empty NBTTagCompound and uses {@link #copy(byte[])} method to fill it with data.
     * @return a new empty NBTTagCompound.
     */
    static INBTTagCompound get(byte[] bytes){
        INBTTagCompound tagCompound = get();
        tagCompound.copy(bytes);

        return tagCompound;
    }

    /**
     * Returns a new empty NBTTagCompound and uses {@link #copy(ItemStack)} method to fill it with data.
     * @return a new empty NBTTagCompound.
     */
    static INBTTagCompound get(ItemStack item){
        INBTTagCompound tagCompound = get();
        tagCompound.copy(item);

        return tagCompound;
    }

    /**
     * Returns a new empty NBTTagCompound and uses {@link #copy(Entity)} method to fill it with data.
     * @return a new empty NBTTagCompound.
     */
    static INBTTagCompound get(Entity entity){
        INBTTagCompound tagCompound = get();
        tagCompound.copy(entity);

        return tagCompound;
    }
}
