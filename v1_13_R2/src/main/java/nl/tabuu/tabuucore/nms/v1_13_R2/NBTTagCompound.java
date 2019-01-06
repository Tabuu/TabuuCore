package nl.tabuu.tabuucore.nms.v1_13_R2;

import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.NBTBase;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;

public class NBTTagCompound implements INBTTagCompound {

    private net.minecraft.server.v1_13_R2.NBTTagCompound _tagCompound;

    public NBTTagCompound(){
        _tagCompound = new net.minecraft.server.v1_13_R2.NBTTagCompound();
    }

    @Override
    public org.bukkit.inventory.ItemStack apply(org.bukkit.inventory.ItemStack item) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(item);

        net.minecraft.server.v1_13_R2.NBTTagCompound oldTagCompound = itemStack.getTag();
//        if(oldTagCompound != null){
//            for(String key : oldTagCompound.getKeys()){
//                NBTBase base = oldTagCompound.get(key).clone();
//                _tagCompound.set(key, base);
//            }
//        }

        itemStack.setTag(_tagCompound);
        item.setItemMeta(CraftItemStack.getItemMeta(itemStack));

        return item;
    }

    @Override
    public INBTTagCompound copy(org.bukkit.inventory.ItemStack item) {
        _tagCompound = CraftItemStack.asNMSCopy(item).getOrCreateTag();
        return this;
    }

    @Override
    public boolean hasKey(String key) {
        return _tagCompound.hasKey(key);
    }

    @Override
    public void removeKey(String key) {
        _tagCompound.remove(key);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        _tagCompound.setBoolean(key, value);
    }

    @Override
    public void setByte(String key, byte value) {
        _tagCompound.setByte(key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        _tagCompound.setByteArray(key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        _tagCompound.setDouble(key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        _tagCompound.setFloat(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        _tagCompound.setInt(key, value);
    }

    @Override
    public void setIntArray(String key, int[] value) {
        _tagCompound.setIntArray(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        _tagCompound.setLong(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        _tagCompound.setShort(key, value);
    }

    @Override
    public void setString(String key, String value) {
        _tagCompound.setString(key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return _tagCompound.getBoolean(key);
    }

    @Override
    public byte getByte(String key) {
        return _tagCompound.getByte(key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return _tagCompound.getByteArray(key);
    }

    @Override
    public double getDouble(String key) {
        return _tagCompound.getDouble(key);
    }

    @Override
    public float getFloat(String key) {
        return _tagCompound.getFloat(key);
    }

    @Override
    public int getInt(String key) {
        return _tagCompound.getInt(key);
    }

    @Override
    public int[] getIntArray(String key) {
        return _tagCompound.getIntArray(key);
    }

    @Override
    public long getLong(String key) {
        return _tagCompound.getLong(key);
    }

    @Override
    public short getShort(String key) {
        return _tagCompound.getShort(key);
    }

    @Override
    public String getString(String key) {
        return _tagCompound.getString(key);
    }
}
