package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StringSerializer extends AbstractByteSerializer<String> {
    @Override
    public byte[] serialize(String object) {
        if(Objects.isNull(object)) return null;
        return object.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return new String(value);
    }
}
