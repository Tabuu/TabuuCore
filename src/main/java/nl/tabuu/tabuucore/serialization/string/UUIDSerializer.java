package nl.tabuu.tabuucore.serialization.string;

import java.util.UUID;

public class UUIDSerializer extends AbstractStringSerializer<UUID> {
    @Override
    public String serialize(UUID object) {
        return object.toString();
    }

    @Override
    public UUID deserialize(String string) {
        return UUID.fromString(string);
    }
}
