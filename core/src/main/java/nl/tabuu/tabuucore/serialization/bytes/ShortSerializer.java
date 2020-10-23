package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.util.Objects;

public class ShortSerializer extends AbstractByteSerializer<Short> {
    @Override
    public byte[] serialize(Short object) {
        if(Objects.isNull(object)) return null;
        return ByteBuffer.allocate(Short.BYTES).putShort(object).array();
    }

    @Override
    public Short deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return ByteBuffer.allocate(Short.BYTES).put(value).getShort(0);
    }
}
