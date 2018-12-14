package nl.tabuu.tabuucore.serialization;

public interface IObjectSerializer<F, T> {
    T serialize(F object);
    F deserialize(T value);
}
