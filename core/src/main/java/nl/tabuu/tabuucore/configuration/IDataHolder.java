package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public interface IDataHolder {

    // region Primitive Types

    String getString(String path);
    default String getString(String path, String def) { String value = getString(path); return Objects.isNull(value) ? def : value; }
    List<String> getStringList(String path);
    default List<String> getStringList(String path, List<String> def) { List<String> list = getStringList(path); return Objects.isNull(list) ? def : list; }

    Character getCharacter(String path);
    default Character getCharacter(String path, char def) { Character value = getCharacter(path); return Objects.isNull(value) ? def : value; }
    List<Character> getCharacterList(String path);
    default List<Character> getCharacterList(String path, List<Character> def) { List<Character> list = getCharacterList(path); return Objects.isNull(list) ? def : list; }

    Boolean getBoolean(String path);
    default Boolean getBoolean(String path, boolean def) { Boolean value = getBoolean(path); return Objects.isNull(value) ? def : value; }
    List<Boolean> getBooleanList(String path);
    default List<Boolean> getBooleanList(String path, List<Boolean> def) { List<Boolean> list = getBooleanList(path); return Objects.isNull(list) ? def : list; }

    Byte getByte(String path);
    default Byte getByte(String path, byte def) { Byte value = getByte(path); return Objects.isNull(value) ? def : value; }
    List<Byte> getByteList(String path);
    default List<Byte> getByteList(String path, List<Byte> def) { List<Byte> list = getByteList(path); return Objects.isNull(list) ? def : list; }

    Double getDouble(String path);
    default Double getDouble(String path, double def) { Double value = getDouble(path); return Objects.isNull(value) ? def : value; }
    List<Double> getDoubleList(String path);
    default List<Double> getDoubleList(String path, List<Double> def) { List<Double> list = getDoubleList(path); return Objects.isNull(list) ? def : list; }

    Float getFloat(String path);
    default Float getFloat(String path, float def) { Float value = getFloat(path); return Objects.isNull(value) ? def : value; }
    List<Float> getFloatList(String path);
    default List<Float> getFloatList(String path, List<Float> def) { List<Float> list = getFloatList(path); return Objects.isNull(list) ? def : list; }

    Integer getInteger(String path);
    default Integer getInteger(String path, int def) { Integer value = getInteger(path); return Objects.isNull(value) ? def : value; }
    List<Integer> getIntegerList(String path);
    default List<Integer> getIntegerList(String path, List<Integer> def) { List<Integer> list = getIntegerList(path); return Objects.isNull(list) ? def : list; }

    Long getLong(String path);
    default Long getLong(String path, long def) { Long value = getLong(path); return Objects.isNull(value) ? def : value; }
    List<Long> getLongList(String path);
    default List<Long> getLongList(String path, List<Long> def) { List<Long> list = getLongList(path); return Objects.isNull(list) ? def : list; }

    Short getShort(String path);
    default Short getShort(String path, short def) { Short value = getShort(path); return Objects.isNull(value) ? def : value; }
    List<Short> getShortList(String path);
    default List<Short> geShortList(String path, List<Short> def) { List<Short> list = getShortList(path); return Objects.isNull(list) ? def : list; }

    void set(String path, String value);
    void setStringList(String path, List<String> list);
    default void setStringMap(String path, Map<String, String> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    void set(String path, char value);
    void setCharacterList(String path, List<Character> list);
    default void setCharacterMap(String path, Map<String, Character> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }


    void set(String path, boolean value);
    void setBooleanList(String path, List<Boolean> list);
    default void setBooleanMap(String path, Map<String, Boolean> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    void set(String path, Number value);
    void setNumberList(String path, List<Number> list);
    default void setNumberMap(String path, Map<String, Number> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    // endregion

    // region Special Types

    /**
     * Returns a {@link Dictionary} from the specified path.
     *
     * @param path The path to get the {@link Dictionary} from.
     * @return A {@link Dictionary} from the specified path.
     */
    default Dictionary getDictionary(String path) {
        Dictionary dictionary = new Dictionary();
        getMap(path).forEach(dictionary::put);

        return dictionary;
    }

    Set<String> getKeys(boolean deep);

    Set<String> getKeys(String path, boolean deep);

    // endregion

    // region Operations

    /**
     * Deletes a specified path.
     *
     * @param path The path to be deleted.
     */
    void delete(String path);

    // endregion

    /**
     * Gets a value from a path, using a deserializer. If the value is null, return the default value.
     * @param path          Path to get the value from.
     * @param deserializer  Deserializer to deserialize the value with.
     * @param def           The default value to return if null.
     * @param <V>           Type of the value.
     * @return              Value at the given path, or the default value if null.
     */
    default <V> V getOrDefault(String path, IObjectDeserializer<String, V> deserializer, V def) {
        V value = get(path, deserializer);
        return value == null ? def : value;
    }

    /**
     * Gets a value from a path, using a deserializer.
     *
     * @param path         Path to get the value from.
     * @param deserializer Deserializer to deserialize the value with.
     * @param <V>          Type of the value.
     * @return Value at the given path.
     */
    default <V> V get(String path, IObjectDeserializer<String, V> deserializer) {
        String value = getString(path);
        return value == null ? null : deserializer.deserialize(value);
    }

    /**
     * Sets a value to a specified path, using a serializer.
     *
     * @param path       Path to set the value to.
     * @param value      Value to be set to the path.
     * @param serializer Serializer to be used to serialize the value.
     * @param <V>        Type of the value.
     */
    default <V> void set(String path, V value, IObjectSerializer<V, String> serializer) {
        set(path, serializer.serialize(value));
    }

    /**
     * Gets a list from a path, using a deserializer.
     *
     * @param path         Path to get the list from.
     * @param deserializer Deserializer to deserialize the list items with.
     * @param <V>          Type of the list items.
     * @return List at the given path.
     */
    default <V> List<V> getList(String path, IObjectDeserializer<String, V> deserializer) {
        List<String> stringList = getStringList(path);
        return stringList.stream().map(deserializer::deserialize).collect(Collectors.toList());
    }

    /**
     * Sets a list to a specified path, using a serializer.
     *
     * @param path       Path to set the list to.
     * @param list       List to be set to the path.
     * @param serializer Serializer to be used to serialize the list items.
     * @param <V>        Type of the list items.
     */
    default <V> void setList(String path, List<V> list, IObjectSerializer<V, String> serializer) {
        List<String> stringList = list.stream().map(serializer::serialize).collect(Collectors.toList());
        setStringList(path, stringList);
    }

    /**
     * Returns a {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     *
     * @param path The path to get the {@link Map} from.
     * @return A {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     */
    default Map<String, String> getMap(String path) {
        return getMap(path, Serializer.STRING);
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     *
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String map)}, but converts the value with a deserializer.
     */
    default <V> Map<String, V> getMap(String path, IObjectDeserializer<String, V> valueDeserializer) {
        return getMap(path, Serializer.STRING, valueDeserializer);
    }

    /**
     * Returns the same as {@link #getMap(String path)} and {@link #getMap(String, IObjectDeserializer)}, but converts the key with a deserializer.
     *
     * @param keyDeserializer   The deserializer to deserialize the key from string.
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <K>               The type to convert the key to.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String path)} and {@link #getMap(String path, IObjectDeserializer valueSerializer)}, but converts the key with a (de)serializer.
     */
    default <K, V> Map<K, V> getMap(String path, IObjectDeserializer<String, K> keyDeserializer, IObjectDeserializer<String, V> valueDeserializer) {
        HashMap<K, V> map = new HashMap<>();
        Set<String> keys = getKeys(path, false);

        for (String key : keys) {
            String value = getString(path + "." + key);
            map.put(keyDeserializer.deserialize(key), valueDeserializer.deserialize(value));
        }
        return map;
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path Path to set the map to.
     */
    default void setMap(String path, Map<String, String> values) {
        setMap(path, values, Serializer.STRING);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <V>             Type of the value.
     */
    default <V> void setMap(String path, Map<String, V> map, IObjectSerializer<V, String> valueSerializer) {
        setMap(path, map, Serializer.STRING, valueSerializer);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     *
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param keySerializer   Serializer used to serialize the key.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <K>             Type of the key.
     * @param <V>             Type of the value.
     */
    default <K, V> void setMap(String path, Map<K, V> map, IObjectSerializer<K, String> keySerializer, IObjectSerializer<V, String> valueSerializer) {
        if (path.isEmpty())
            throw new IllegalArgumentException("Cannot set to an empty path!");

        delete(path);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            String key = keySerializer.serialize(entry.getKey());
            String value = valueSerializer.serialize(entry.getValue());
            set(path + "." + key, value);
        }
    }
}
