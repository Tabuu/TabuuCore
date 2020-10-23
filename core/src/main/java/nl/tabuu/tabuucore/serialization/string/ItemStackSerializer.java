package nl.tabuu.tabuucore.serialization.string;

import nl.tabuu.tabuucore.serialization.bytes.Serializer;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;
import java.util.Objects;

public class ItemStackSerializer extends AbstractStringSerializer<ItemStack> {
    @Override
    public String serialize(ItemStack itemStack) {
        if(Objects.isNull(itemStack)) return null;

        return serializeArray(itemStack);
    }

    @Override
    public ItemStack deserialize(String string) {
        if(Objects.isNull(string)) return null;

        ItemStack[] itemStacks = deserializeArray(string);
        if(itemStacks.length < 1)
            return null;
        return itemStacks[0];
    }

    @Override
    public ItemStack[] deserializeArray(String string) {
        if(Objects.isNull(string)) return null;

        return Serializer.ITEMSTACK.deserialize(Base64.getDecoder().decode(string));
    }

    @Override
    public String serializeArray(ItemStack... itemStacks) {
        if(Objects.isNull(itemStacks)) return null;

        return Base64.getEncoder().encodeToString(Serializer.ITEMSTACK.serialize(itemStacks));
    }
}
