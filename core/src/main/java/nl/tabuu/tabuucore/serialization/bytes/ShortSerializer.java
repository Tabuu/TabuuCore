package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;

public class ShortSerializer extends AbstractByteSerializer<Short> {
    @Override
    public byte[] serialize(Short object) {
        return ByteBuffer.allocate(Short.BYTES).putShort(object).array();
    }

    @Override
    public Short deserialize(byte[] value) {
        return ByteBuffer.allocate(Short.BYTES).put(value).getShort(0);
    }
}
