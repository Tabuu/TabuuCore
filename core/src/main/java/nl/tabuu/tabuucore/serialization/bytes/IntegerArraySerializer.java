package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IntegerArraySerializer extends AbstractByteSerializer<int[]> {
    @Override
    public byte[] serialize(int[] object) {
        byte[] bytes = new byte[Integer.BYTES * object.length];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer().put(object);

        return bytes;
    }

    @Override
    public int[] deserialize(byte[] value) {
        return ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).asIntBuffer().array();
    }
}
