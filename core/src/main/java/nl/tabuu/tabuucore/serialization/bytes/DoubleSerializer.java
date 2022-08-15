package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.util.Objects;

public class DoubleSerializer extends AbstractByteSerializer<Double> {
    @Override
    public byte[] serialize(Double object) {
        if(Objects.isNull(object)) return null;
        return ByteBuffer.allocate(Double.BYTES).putDouble(object).array();
    }

    @Override
    public Double deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return ByteBuffer.allocate(Double.BYTES).put(value).getDouble(0);
    }
}
