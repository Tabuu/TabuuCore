package nl.tabuu.tabuucore.serialization;

public interface IArraySerializer<F, T> {
    T serializeArray(F[] object);
    F[] deserializeArray(T value);
}
