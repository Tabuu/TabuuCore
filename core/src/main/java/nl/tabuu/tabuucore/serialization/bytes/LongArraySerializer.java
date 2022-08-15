package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public class LongArraySerializer extends AbstractByteSerializer<long[]> {
    @Override
    public byte[] serialize(long[] object) {
        if(Objects.isNull(object)) return null;

        byte[] bytes = new byte[Long.BYTES * object.length];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asLongBuffer().put(object);
        return bytes;
    }

    @Override
    public long[] deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;

        long[] longArray = new long[value.length / Long.BYTES];
        ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).asLongBuffer().get(longArray);
        return longArray;
    }
}
