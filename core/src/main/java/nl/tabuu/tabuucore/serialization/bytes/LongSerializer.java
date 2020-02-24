package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;

public class LongSerializer extends AbstractByteSerializer<Long> {
    @Override
    public byte[] serialize(Long object) {
        return ByteBuffer.allocate(Long.BYTES).putLong(object).array();
    }

    @Override
    public Long deserialize(byte[] value) {
        return ByteBuffer.allocate(Long.BYTES).put(value).getLong(0);
    }
}
