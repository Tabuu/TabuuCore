package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.charset.StandardCharsets;

public class StringSerializer extends AbstractByteSerializer<String> {
    @Override
    public byte[] serialize(String object) {
        return object.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String deserialize(byte[] value) {
        return new String(value);
    }
}
