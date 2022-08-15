package nl.tabuu.tabuucore.serialization;

import nl.tabuu.tabuucore.configuration.IDataHolder;

/**
 * This class should have a constructor that accepts T, OR a static function called deserialize that accepts T, and returns a Serializable object.
 * @param <T> The serialize type.
 */
public interface ISerializable<T extends IDataHolder> {
    /**
     * Fills the IDataHolder with the data of this object.
     * @param data The data to fill.
     * @return The filled IDataHolder.
     */
    T serialize(T data);
}
