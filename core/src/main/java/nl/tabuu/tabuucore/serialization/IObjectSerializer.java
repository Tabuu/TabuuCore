package nl.tabuu.tabuucore.serialization;

import java.io.IOException;

public interface IObjectSerializer<F, T> {
    T serialize(F object);
    F deserialize(T value) throws IOException;
}
