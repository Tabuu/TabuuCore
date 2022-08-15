package nl.tabuu.tabuucore.serialization.bytes;

public class ByteArraySerializer extends AbstractByteSerializer<byte[]> {
    @Override
    public byte[] serialize(byte[] object) {
        return object;
    }

    @Override
    public byte[] deserialize(byte[] value) {
        return value;
    }
}
