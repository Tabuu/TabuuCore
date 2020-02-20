package nl.tabuu.tabuucore.serialization.string;

import nl.tabuu.tabuucore.serialization.bytes.Serializer;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

public class ItemStackSerializer extends AbstractStringSerializer<ItemStack> {
    @Override
    public String serialize(ItemStack itemStack) {
        return serializeArray(itemStack);
    }

    @Override
    public ItemStack deserialize(String string) {
        ItemStack[] itemStacks = deserializeArray(string);
        if(itemStacks.length < 1)
            return null;
        return itemStacks[0];
    }

    @Override
    public ItemStack[] deserializeArray(String string) {
        return Serializer.ITEMSTACK.deserialize(Base64.getDecoder().decode(string));
    }

    @Override
    public String serializeArray(ItemStack... itemStacks) {
        return Base64.getEncoder().encodeToString(Serializer.ITEMSTACK.serialize(itemStacks));
    }
}
