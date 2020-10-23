package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.util.Objects;

public class LongSerializer extends AbstractByteSerializer<Long> {
    @Override
    public byte[] serialize(Long object) {
        if(Objects.isNull(object)) return null;
        return ByteBuffer.allocate(Long.BYTES).putLong(object).array();
    }

    @Override
    public Long deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return ByteBuffer.allocate(Long.BYTES).put(value).getLong(0);
    }
}
