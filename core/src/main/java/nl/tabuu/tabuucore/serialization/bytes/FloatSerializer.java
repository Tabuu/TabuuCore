package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.util.Objects;

public class FloatSerializer extends AbstractByteSerializer<Float> {
    @Override
    public byte[] serialize(Float object) {
        if(Objects.isNull(object)) return null;
        return ByteBuffer.allocate(Float.BYTES).putFloat(object).array();
    }

    @Override
    public Float deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return ByteBuffer.allocate(Float.BYTES).put(value).getFloat(0);
    }
}
