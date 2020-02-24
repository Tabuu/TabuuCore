package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LongArraySerializer extends AbstractByteSerializer<long[]> {
    @Override
    public byte[] serialize(long[] object) {
        byte[] bytes = new byte[Long.BYTES * object.length];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asLongBuffer().put(object);
        return bytes;
    }

    @Override
    public long[] deserialize(byte[] value) {
        long[] longArray = new long[value.length / Long.BYTES];
        ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).asLongBuffer().get(longArray);
        return longArray;
    }
}
