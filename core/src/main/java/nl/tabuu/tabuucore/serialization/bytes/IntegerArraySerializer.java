package nl.tabuu.tabuucore.serialization.bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Objects;

public class IntegerArraySerializer extends AbstractByteSerializer<int[]> {
    @Override
    public byte[] serialize(int[] object) {
        if(Objects.isNull(object)) return null;
        byte[] bytes = new byte[Integer.BYTES * object.length];
        ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer().put(object);

        return bytes;
    }

    @Override
    public int[] deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;

        IntBuffer buffer = ByteBuffer.wrap(value).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] array = new int[buffer.remaining()];
        buffer.get(array);

        return array;
    }
}
