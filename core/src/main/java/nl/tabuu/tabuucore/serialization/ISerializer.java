package nl.tabuu.tabuucore.serialization;

public interface ISerializer<F, T> extends IArraySerializer<F, T>, IArrayDeserializer<T, F>, IObjectSerializer<F, T>, IObjectDeserializer<T, F> {
}
