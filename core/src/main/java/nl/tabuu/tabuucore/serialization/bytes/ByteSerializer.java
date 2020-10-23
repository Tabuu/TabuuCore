package nl.tabuu.tabuucore.serialization.bytes;

import java.util.Objects;

public class ByteSerializer extends AbstractByteSerializer<Byte> {
    @Override
    public byte[] serialize(Byte object) {
        return Objects.isNull(object) ? null : new byte[]{ object };
    }

    @Override
    public Byte deserialize(byte[] value) {
        if(Objects.isNull(value)) return null;
        return value.length > 0 ? value[0] : null;
    }
}
