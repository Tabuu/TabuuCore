package nl.tabuu.tabuucore.serialization;

public interface IArrayDeserializer<T, F> {
    F[] deserializeArray(T value);
}
