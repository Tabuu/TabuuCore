package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.util.Objects;

public class IntegerSerializer extends AbstractByteSerializer<Integer> {
    @Override
    public byte[] serialize(Integer object) {
        if(Objects.isNull(object)) return null;
        return ByteBuffer.allocate(Integer.BYTES).putInt(object).array();
    }

    @Override
    public Integer deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return ByteBuffer.allocate(Integer.BYTES).put(value).getInt(0);
    }
}
