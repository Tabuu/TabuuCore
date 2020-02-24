package nl.tabuu.tabuucore.serialization;

public interface IObjectSerializer<T, F> {
    F serialize(T object);
    T deserialize(F value);
}
