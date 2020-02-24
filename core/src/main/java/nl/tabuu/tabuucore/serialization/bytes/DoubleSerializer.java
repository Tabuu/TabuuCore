package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;

public class DoubleSerializer extends AbstractByteSerializer<Double> {
    @Override
    public byte[] serialize(Double object) {
        return ByteBuffer.allocate(Double.BYTES).putDouble(object).array();
    }

    @Override
    public Double deserialize(byte[] value) {
        return ByteBuffer.allocate(Double.BYTES).put(value).getDouble(0);
    }
}
