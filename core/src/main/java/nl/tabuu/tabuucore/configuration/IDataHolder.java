package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.stream.Collectors;

public interface IDataHolder {

    // region Essential Types

    /**
     * Returns the string at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The string at the specified path, or else null.
     */
    String getString(String path);
    /**
     * Returns the string at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the string at the specified path, or else the default value.
     */
    default String getString(String path, String def) { String value = getString(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the string-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The string-list at the specified path, or else an empty list.
     */
    List<String> getStringList(String path);
    /**
     * Returns the string-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the string-list at the specified path, or else the default value.
     */
    default List<String> getStringList(String path, List<String> def) { List<String> list = getStringList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the character at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The character at the specified path, or else null.
     */
    Character getCharacter(String path);
    /**
     * Returns the character at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the character at the specified path, or else the default value.
     */
    default Character getCharacter(String path, char def) { Character value = getCharacter(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the character-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The character-list at the specified path, or else an empty list.
     */
    List<Character> getCharacterList(String path);
    /**
     * Returns the character-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the character-list at the specified path, or else the default value.
     */
    default List<Character> getCharacterList(String path, List<Character> def) { List<Character> list = getCharacterList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the boolean at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The boolean at the specified path, or else null.
     */
    Boolean getBoolean(String path);
    /**
     * Returns the boolean at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the boolean at the specified path, or else the default value.
     */
    default Boolean getBoolean(String path, boolean def) { Boolean value = getBoolean(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the boolean-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The boolean-list at the specified path, or else an empty list.
     */
    List<Boolean> getBooleanList(String path);
    /**
     * Returns the boolean-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the boolean-list at the specified path, or else the default value.
     */
    default List<Boolean> getBooleanList(String path, List<Boolean> def) { List<Boolean> list = getBooleanList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the byte at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The byte at the specified path, or else null.
     */
    Byte getByte(String path);
    /**
     * Returns the byte at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the byte at the specified path, or else the default value.
     */
    default Byte getByte(String path, byte def) { Byte value = getByte(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the byte-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The byte-list at the specified path, or else an empty list.
     */
    List<Byte> getByteList(String path);
    /**
     * Returns the byte-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the byte-list at the specified path, or else the default value.
     */
    default List<Byte> getByteList(String path, List<Byte> def) { List<Byte> list = getByteList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the double at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The double at the specified path, or else null.
     */
    Double getDouble(String path);
    /**
     * Returns the double at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the double at the specified path, or else the default value.
     */
    default Double getDouble(String path, double def) { Double value = getDouble(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the double-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The double-list at the specified path, or else an empty list.
     */
    List<Double> getDoubleList(String path);
    /**
     * Returns the double-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the double-list at the specified path, or else the default value.
     */
    default List<Double> getDoubleList(String path, List<Double> def) { List<Double> list = getDoubleList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the float at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The float at the specified path, or else null.
     */
    Float getFloat(String path);
    /**
     * Returns the float at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the float at the specified path, or else the default value.
     */
    default Float getFloat(String path, float def) { Float value = getFloat(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the float-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The float-list at the specified path, or else an empty list.
     */
    List<Float> getFloatList(String path);
    /**
     * Returns the float-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the float-list at the specified path, or else the default value.
     */
    default List<Float> getFloatList(String path, List<Float> def) { List<Float> list = getFloatList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the integer at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The integer at the specified path, or else null.
     */
    Integer getInteger(String path);
    /**
     * Returns the integer at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the integer at the specified path, or else the default value.
     */
    default Integer getInteger(String path, int def) { Integer value = getInteger(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the integer-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The integer-list at the specified path, or else an empty list.
     */
    List<Integer> getIntegerList(String path);
    /**
     * Returns the integer-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the integer-list at the specified path, or else the default value.
     */
    default List<Integer> getIntegerList(String path, List<Integer> def) { List<Integer> list = getIntegerList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the long at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The long at the specified path, or else null.
     */
    Long getLong(String path);
    /**
     * Returns the long at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the long at the specified path, or else the default value.
     */
    default Long getLong(String path, long def) { Long value = getLong(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the long-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The long-list at the specified path, or else an empty list.
     */
    List<Long> getLongList(String path);
    /**
     * Returns the long-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the long-list at the specified path, or else the default value.
     */
    default List<Long> getLongList(String path, List<Long> def) { List<Long> list = getLongList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Returns the short at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The short at the specified path, or else null.
     */
    Short getShort(String path);
    /**
     * Returns the short at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the short at the specified path, or else the default value.
     */
    default Short getShort(String path, short def) { Short value = getShort(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the short-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The short-list at the specified path, or else an empty list.
     */
    List<Short> getShortList(String path);
    /**
     * Returns the short-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return Returns the short-list at the specified path, or else the default value.
     */
    default List<Short> geShortList(String path, List<Short> def) { List<Short> list = getShortList(path); return Objects.isNull(list) ? def : list; }

    /**
     * Sets the specified string to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(String path, String value);
    /**
     * Sets the specified string-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setStringList(String path, List<String> list);
    /**
     * Sets the specified string-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setStringMap(String path, Map<String, String> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    /**
     * Sets the specified character to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(String path, char value);
    /**
     * Sets the specified character-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setCharacterList(String path, List<Character> list);
    /**
     * Sets the specified character-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setCharacterMap(String path, Map<String, Character> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    /**
     * Sets the specified boolean to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(String path, boolean value);
    /**
     * Sets the specified boolean-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setBooleanList(String path, List<Boolean> list);
    /**
     * Sets the specified boolean-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setBooleanMap(String path, Map<String, Boolean> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    /**
     * Sets the specified number to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(String path, Number value);
    /**
     * Sets the specified number-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setNumberList(String path, List<Number> list);
    /**
     * Sets the specified number-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setNumberMap(String path, Map<String, Number> map) { delete(path); map.forEach((child, value) -> set(path + "." + child, value)); }

    // endregion

    // region Special Types

    /**
     * Returns a {@link Dictionary} from the specified path.
     * @param path The path to get the {@link Dictionary} from.
     * @return A {@link Dictionary} from the specified path.
     */
    default Dictionary getDictionary(String path) {
        Dictionary dictionary = new Dictionary();
        getMap(path).forEach(dictionary::put);

        return dictionary;
    }

    /**
     * Returns a DataHolder from the specified path.
     * @param path The path to get the DataHolder from.
     * @return A DataHolder from the specified path.
     */
    IDataHolder getDataSection(String path);

    /**
     * Sets a DataHolder to a specified path.
     * @param path The path to set the DataHolder to.
     * @param data The DataHolder to set to the specified path.
     */
    void setDataSection(String path, IDataHolder data);

    /**
     * Returns a list of keys which are relative to this DataHolder, and NOT to the path.
     * @param deep When set to true, all child-keys will also be returned.
     * @return A list of keys which are relative to this DataHolder, and NOT to the path.
     */
    Set<String> getKeys(boolean deep);

    /**
     * Returns a list of keys which are relative to this DataHolder, and NOT to the path.
     * @param path The top path to get all underlying keys from, which are relative to the current DataHolder.
     * @param deep When set to true, all child-keys will also be returned.
     * @return A list of keys which are relative to this DataHolder, and NOT to the path.
     */
    Set<String> getKeys(String path, boolean deep);

    // endregion

    // region Operations

    /**
     * Deletes a specified path.
     * @param path The path to be deleted.
     */
    void delete(String path);

    /**
     * Creates an empty DataHolder at the given path, and returns the created DataHolder.
     * @param path The path to create the DataHolder at.
     * @return The created DataHolder.
     */
    IDataHolder createSection(String path);

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
     * @param path The path to get the {@link Map} from.
     * @return A {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     */
    default Map<String, String> getMap(String path) {
        return getMap(path, Serializer.STRING);
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
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
     * @param keyDeserializer   The deserializer to deserialize the key from string.
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <K>               The type to convert the key to.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String path)} and {@link #getMap(String path, IObjectDeserializer valueSerializer)}, but converts the key with a (de)serializer.
     */
    default <K, V> Map<K, V> getMap(String path, IObjectDeserializer<String, K> keyDeserializer, IObjectDeserializer<String, V> valueDeserializer) {
        HashMap<K, V> map = new HashMap<>();
        Set<String> keys = getDataSection(path).getKeys(false);

        for (String stringKey : keys) {
            String stringValue = getString(path + "." + stringKey);

            K key;
            V value;

            try {
                key = keyDeserializer.deserialize(stringKey);
                value = valueDeserializer.deserialize(stringValue);
            } catch (Exception ignore) { continue; }

            map.put(key, value);
        }
        return map;
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     * @param path Path to set the map to.
     */
    default void setMap(String path, Map<String, String> values) {
        setMap(path, values, Serializer.STRING);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
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
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param keySerializer   Serializer used to serialize the key.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <K>             Type of the key.
     * @param <V>             Type of the value.
     */
    default <K, V> void setMap(String path, Map<K, V> map, IObjectSerializer<K, String> keySerializer, IObjectSerializer<V, String> valueSerializer) {
        delete(path);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            String key = keySerializer.serialize(entry.getKey());
            String value = valueSerializer.serialize(entry.getValue());
            set(path + "." + key, value);
        }
    }
}
