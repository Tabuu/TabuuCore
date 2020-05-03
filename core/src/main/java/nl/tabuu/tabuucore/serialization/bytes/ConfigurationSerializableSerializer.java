package nl.tabuu.tabuucore.serialization.bytes;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConfigurationSerializableSerializer extends AbstractByteSerializer<ConfigurationSerializable[]> {

    @Override
    public byte[] serialize(ConfigurationSerializable... serializables) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(serializables.length);

            for (ConfigurationSerializable serializable : serializables) {
                dataOutput.writeObject(serializable);
            }

            dataOutput.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to convert serializable to bytes.", e);
        }
    }

    @Override
    public ConfigurationSerializable[] deserialize(byte[] data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++)
                items[i] = (ItemStack) dataInput.readObject();

            dataInput.close();

            return items;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Unable to convert bytes to items.", e);
        }
    }

}
