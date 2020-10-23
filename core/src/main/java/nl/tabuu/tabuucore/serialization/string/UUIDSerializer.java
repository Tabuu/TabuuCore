package nl.tabuu.tabuucore.serialization.string;

import java.util.Objects;
import java.util.UUID;

public class UUIDSerializer extends AbstractStringSerializer<UUID> {
    @Override
    public String serialize(UUID object) {
        if(Objects.isNull(object)) return null;

        return object.toString();
    }

    @Override
    public UUID deserialize(String string) {
        if(Objects.isNull(string)) return null;

        return UUID.fromString(string);
    }
}
