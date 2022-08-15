package nl.tabuu.tabuucore.nms.v1_17_R1;

import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntity;
import nl.tabuu.tabuucore.nms.NBTTagType;
import nl.tabuu.tabuucore.nms.wrapper.INBTTagCompound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NBTTagCompound implements INBTTagCompound {

    private net.minecraft.nbt.NBTTagCompound _tagCompound;

    public NBTTagCompound() {
        _tagCompound = new net.minecraft.nbt.NBTTagCompound();
    }

    public NBTTagCompound(net.minecraft.nbt.NBTTagCompound tagCompound) {
        _tagCompound = tagCompound;
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
        nmsEntity.load(_tagCompound);
        return nmsEntity.getBukkitEntity();
    }

    @Override
    public Block apply(Block block) {
        Location location = block.getLocation();
        BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        CraftWorld world = ((CraftWorld) block.getWorld());

        TileEntity tileEntity = world.getHandle().getTileEntity(position);
        if (tileEntity == null) return null;

        tileEntity.load(_tagCompound);
        return block;
    }

    @Override
    public INBTTagCompound copy(org.bukkit.inventory.ItemStack item) {
        _tagCompound = CraftItemStack.asNMSCopy(item).getOrCreateTag();
        return this;
    }

    @Override
    public INBTTagCompound copy(org.bukkit.entity.Entity entity) {
        Entity nmsEntity = ((CraftEntity) entity).getHandle();
        _tagCompound = nmsEntity.save(_tagCompound);

        return this;
    }

    @Override
    public INBTTagCompound copy(Block block) {
        Location location = block.getLocation();
        BlockPosition position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        CraftWorld world = ((CraftWorld) block.getWorld());

        TileEntity tileEntity = world.getHandle().getTileEntity(position);
        if (tileEntity != null)
            _tagCompound = tileEntity.save(_tagCompound);

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
    public INBTTagCompound copyRawByteArray(byte[] bytes) {
        _tagCompound = (net.minecraft.nbt.NBTTagCompound) baseFromByteArray(NBTTagType.COMPOUND, bytes);
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
    public INBTTagCompound setTagCompound(String key) {
        NBTTagCompound tagCompound = new NBTTagCompound();
        _tagCompound.set(key, tagCompound._tagCompound);
        return tagCompound;
    }

    @Override
    public void setTagCompound(String key, INBTTagCompound value) {
        _tagCompound.set(key, ((NBTTagCompound) value)._tagCompound);
    }

    @Override
    public <T> void setList(String key, List<T> list) {
        if (list.isEmpty())
            return;

        NBTTagList tagList = new NBTTagList();
        T sample = list.get(0);

        NBTTagType type = NBTTagType.valueOf(sample.getClass());

        for (T item : list) {
            byte[] bytes = type.toByteArray(item);
            NBTBase base = baseFromByteArray(type, bytes);

            tagList.b(0, base);
        }
        _tagCompound.set(key, tagList);
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
    public INBTTagCompound getTagCompound(String key) {
        return new NBTTagCompound(_tagCompound.getCompound(key));
    }

    @Override
    public <T> List<T> getList(NBTTagType type, Class<T> clazz, String key) {
        List<T> list = new ArrayList<>();

        for (NBTBase base : _tagCompound.getList(key, type.ordinal())) {
            byte[] bytes = baseToByteArray(base);
            T object = (T) type.fromBytes(bytes);

            list.add(object);
        }

        return list;
    }

    @Override
    public String getObjectToString(String key) {
        Object object = _tagCompound.get(key);
        return object == null ? "null" : object.toString();
    }

    @Override
    public Set<String> getKeys() {
        return _tagCompound.getKeys();
    }

    @Override
    public byte[] toByteArray() {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            NBTCompressedStreamTools.a(_tagCompound, outputStream);
            bytes = outputStream.toByteArray();
            outputStream.close();
        } catch (IOException ignore) { }

        return bytes;
    }

    @Override
    public byte[] toRawByteArray() {
        return baseToByteArray(_tagCompound);
    }

    @Override
    public String toJson() {
        return _tagCompound.toString();
    }

    @Override
    public String toJson(org.bukkit.inventory.ItemStack item) {
        item = apply(item);
        net.minecraft.nbt.NBTTagCompound compound = new net.minecraft.nbt.NBTTagCompound();
        CraftItemStack.asNMSCopy(item).save(compound);
        return compound.toString();
    }

    protected byte[] baseToByteArray(NBTBase base) {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream byteOStream = new ByteArrayOutputStream();
        DataOutputStream dataOStream = new DataOutputStream(byteOStream);

        try {
            base.write(dataOStream);
            bytes = byteOStream.toByteArray();
            dataOStream.close();
        } catch (IOException ignore) { ignore.printStackTrace(); }

        return bytes;
    }

    protected NBTBase baseFromByteArray(NBTTagType type, byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        DataInputStream dataStream = new DataInputStream(byteStream);

        try {
            net.minecraft.nbt.NBTTagType<?> nmsType = NBTTagTypes.a(type.ordinal());
            return NBTTagTypes.a(type.ordinal()).b(dataStream, 0, NBTReadLimiter.a);
        } catch (IOException ignore) { ignore.printStackTrace();}

        return null;
    }
}
