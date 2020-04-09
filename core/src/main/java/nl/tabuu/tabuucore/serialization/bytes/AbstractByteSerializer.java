package nl.tabuu.tabuucore.serialization.bytes;

import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;

public abstract class AbstractByteSerializer<T> implements IObjectSerializer<T, byte[]>, IObjectDeserializer<byte[], T> {
    @Override
    public abstract byte[] serialize(T object);

    @Override
    public abstract T deserialize(byte[] value);
}
