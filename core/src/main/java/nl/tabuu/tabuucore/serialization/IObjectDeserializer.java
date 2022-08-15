package nl.tabuu.tabuucore.serialization;

public interface IObjectDeserializer<F, T> {
    T deserialize(F value);
}