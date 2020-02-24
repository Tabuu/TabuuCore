package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;

public class FloatSerializer extends AbstractByteSerializer<Float> {
    @Override
    public byte[] serialize(Float object) {
        return ByteBuffer.allocate(Float.BYTES).putFloat(object).array();
    }

    @Override
    public Float deserialize(byte[] value) {
        return ByteBuffer.allocate(Float.BYTES).put(value).getFloat(0);
    }
}
