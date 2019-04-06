package nl.tabuu.tabuucore.nms.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class NBTTagCompound implements INBTTagCompound {

    private net.minecraft.server.v1_8_R3.NBTTagCompound _tagCompound;

    public NBTTagCompound(){
        _tagCompound = new net.minecraft.server.v1_8_R3.NBTTagCompound();
    }

    @Override
    public org.bukkit.inventory.ItemStack apply(org.bukkit.inventory.ItemStack item) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        itemStack.setTag(_tagCompound);
        item.setItemMeta(CraftItemStack.getItemMeta(itemStack));

        return item;
    }

    @Override
    public org.bukkit.entity.Entity apply(org.bukkit.entity.Entity entity) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        nmsEntity.f(_tagCompound);
        return nmsEntity.getBukkitEntity();
    }

    @Override
    public INBTTagCompound copy(org.bukkit.inventory.ItemStack item) {
        _tagCompound = CraftItemStack.asNMSCopy(item).getTag();
        if(_tagCompound == null)
            _tagCompound = new net.minecraft.server.v1_8_R3.NBTTagCompound();
        return this;
    }

    @Override
    public INBTTagCompound copy(org.bukkit.entity.Entity entity) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        _tagCompound = nmsEntity.getNBTTag();
        return this;
    }

    @Override
    public INBTTagCompound copy(byte[] bytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try {
            _tagCompound = NBTCompressedStreamTools.a(inputStream);
        } catch (IOException ignore) { }

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

    @Override
    public String getObjectToString(String key) {
        return _tagCompound.get(key).toString();
    }

    @Override
    public Set<String> getKeys() {
        return _tagCompound.c();
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            NBTCompressedStreamTools.a(_tagCompound, outputStream);
            bytes = outputStream.toByteArray();
            outputStream.close();
        } catch (IOException e) {
            return bytes;
        }

        return bytes;
    }
}
