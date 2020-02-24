package nl.tabuu.tabuucore.serialization.bytes;

import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.ISerializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractByteSerializer<T> implements IObjectSerializer<T, byte[]> {
    @Override
    public abstract byte[] serialize(T object);

    @Override
    public abstract T deserialize(byte[] value);
}
