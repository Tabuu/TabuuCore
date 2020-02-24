package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;

public class IntegerSerializer extends AbstractByteSerializer<Integer> {
    @Override
    public byte[] serialize(Integer object) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(object).array();
    }

    @Override
    public Integer deserialize(byte[] value) {
        return ByteBuffer.allocate(Integer.BYTES).put(value).getInt(0);
    }
}
