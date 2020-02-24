package nl.tabuu.tabuucore.serialization.bytes;

public class ByteSerializer extends AbstractByteSerializer<Byte> {
    @Override
    public byte[] serialize(Byte object) {
        return new byte[]{object};
    }

    @Override
    public Byte deserialize(byte[] value) {
        return value.length > 0 ? value[0] : null;
    }
}
