package nl.tabuu.tabuucore.configuration;

import nl.tabuu.tabuucore.serialization.IObjectDeserializer;
import nl.tabuu.tabuucore.serialization.IObjectSerializer;
import nl.tabuu.tabuucore.serialization.ISerializable;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public interface IDataHolder {

    // region Essential Types
    
    // region Getters
    
    // region String
    /**
     * Returns the string at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The string at the specified path, or else null.
     */
    @Nullable String getString(@Nonnull String path);
    /**
     * Returns the string at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The string at the specified path, or else the default value.
     */
    @Nonnull default String getString(@Nonnull String path, @Nonnull String def) { String value = getString(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the string-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The string-list at the specified path, or else an empty list.
     */
    @Nonnull List<String> getStringList(@Nonnull String path);
    /**
     * Returns the string-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The string-list at the specified path, or else the default value.
     */
    @Nonnull default List<String> getStringList(@Nonnull String path, @Nonnull List<String> def) { List<String> list = getStringList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The key-string-map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, String> getStringMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getString(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The key-string-map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, String> getStringMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, String> def) {
        Map<K, String> map = getStringMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The key-string-map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, String> getStringMap(@Nonnull String path) { return getStringMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The key-string-map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, String> getStringMap(@Nonnull String path, Map<String, String> def) { Map<String, String> map = getStringMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Character
    /**
     * Returns the character at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The character at the specified path, or else null.
     */
    @Nullable Character getCharacter(@Nonnull String path);
    /**
     * Returns the character at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The character at the specified path, or else the default value.
     */
    default char getCharacter(@Nonnull String path, char def) { Character value = getCharacter(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the character-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The character-list at the specified path, or else an empty list.
     */
    @Nonnull List<Character> getCharacterList(@Nonnull String path);
    /**
     * Returns the character-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The character-list at the specified path, or else the default value.
     */
    @Nonnull default List<Character> getCharacterList(@Nonnull String path, @Nonnull List<Character> def) { List<Character> list = getCharacterList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Character> getCharacterMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getCharacter(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Character> getCharacterMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Character> def) {
        Map<K, Character> map = getCharacterMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Character> getCharacterMap(@Nonnull String path) { return getCharacterMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Character> getCharacterMap(@Nonnull String path, Map<String, Character> def) { Map<String, Character> map = getCharacterMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Boolean
    /**
     * Returns the boolean at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The boolean at the specified path, or else null.
     */
    @Nullable Boolean getBoolean(@Nonnull String path);
    /**
     * Returns the boolean at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The boolean at the specified path, or else the default value.
     */
    default boolean getBoolean(@Nonnull String path, boolean def) { Boolean value = getBoolean(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the boolean-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The boolean-list at the specified path, or else an empty list.
     */
    @Nonnull List<Boolean> getBooleanList(@Nonnull String path);
    /**
     * Returns the boolean-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The boolean-list at the specified path, or else the default value.
     */
    @Nonnull default List<Boolean> getBooleanList(@Nonnull String path, @Nonnull List<Boolean> def) { List<Boolean> list = getBooleanList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Boolean> getBooleanMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getBoolean(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Boolean> getBooleanMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Boolean> def) {
        Map<K, Boolean> map = getBooleanMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Boolean> getBooleanMap(@Nonnull String path) { return getBooleanMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Boolean> getBooleanMap(@Nonnull String path, Map<String, Boolean> def) { Map<String, Boolean> map = getBooleanMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Byte
    /**
     * Returns the byte at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The byte at the specified path, or else null.
     */
    @Nullable Byte getByte(@Nonnull String path);
    /**
     * Returns the byte at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The byte at the specified path, or else the default value.
     */
    default byte getByte(@Nonnull String path, byte def) { Byte value = getByte(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the byte-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The byte-list at the specified path, or else an empty list.
     */
    @Nonnull List<Byte> getByteList(@Nonnull String path);
    /**
     * Returns the byte-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The byte-list at the specified path, or else the default value.
     */
    @Nonnull default List<Byte> getByteList(@Nonnull String path, @Nonnull List<Byte> def) { List<Byte> list = getByteList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Byte> getByteMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getByte(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Byte> getByteMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Byte> def) {
        Map<K, Byte> map = getByteMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Byte> getByteMap(@Nonnull String path) { return getByteMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Byte> getByteMap(@Nonnull String path, Map<String, Byte> def) { Map<String, Byte> map = getByteMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Double
    /**
     * Returns the double at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The double at the specified path, or else null.
     */
    @Nullable Double getDouble(@Nonnull String path);
    /**
     * Returns the double at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The double at the specified path, or else the default value.
     */
    default double getDouble(@Nonnull String path, double def) { Double value = getDouble(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the double-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The double-list at the specified path, or else an empty list.
     */
    @Nonnull List<Double> getDoubleList(@Nonnull String path);
    /**
     * Returns the double-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The double-list at the specified path, or else the default value.
     */
    @Nonnull default List<Double> getDoubleList(@Nonnull String path, @Nonnull List<Double> def) { List<Double> list = getDoubleList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Double> getDoubleMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getDouble(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Double> getDoubleMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Double> def) {
        Map<K, Double> map = getDoubleMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Double> getDoubleMap(@Nonnull String path) { return getDoubleMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Double> getDoubleMap(@Nonnull String path, Map<String, Double> def) { Map<String, Double> map = getDoubleMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Float
    /**
     * Returns the float at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The float at the specified path, or else null.
     */
    @Nullable Float getFloat(@Nonnull String path);
    /**
     * Returns the float at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The float at the specified path, or else the default value.
     */
    default float getFloat(@Nonnull String path, float def) { Float value = getFloat(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the float-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The float-list at the specified path, or else an empty list.
     */
    @Nonnull List<Float> getFloatList(@Nonnull String path);
    /**
     * Returns the float-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The float-list at the specified path, or else the default value.
     */
    @Nonnull default List<Float> getFloatList(@Nonnull String path, @Nonnull List<Float> def) { List<Float> list = getFloatList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Float> getFloatMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getFloat(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Float> getFloatMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Float> def) {
        Map<K, Float> map = getFloatMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Float> getFloatMap(@Nonnull String path) { return getFloatMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Float> getFloatMap(@Nonnull String path, Map<String, Float> def) { Map<String, Float> map = getFloatMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Integer
    /**
     * Returns the integer at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The integer at the specified path, or else null.
     */
    @Nullable Integer getInteger(@Nonnull String path);
    /**
     * Returns the integer at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The integer at the specified path, or else the default value.
     */
    default int getInteger(@Nonnull String path, int def) { Integer value = getInteger(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the integer-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The integer-list at the specified path, or else an empty list.
     */
    @Nonnull List<Integer> getIntegerList(@Nonnull String path);
    /**
     * Returns the integer-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The integer-list at the specified path, or else the default value.
     */
    @Nonnull default List<Integer> getIntegerList(@Nonnull String path, @Nonnull List<Integer> def) { List<Integer> list = getIntegerList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Integer> getIntegerMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getInteger(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Integer> getIntegerMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Integer> def) {
        Map<K, Integer> map = getIntegerMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Integer> getIntegerMap(@Nonnull String path) { return getIntegerMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Integer> getIntegerMap(@Nonnull String path, Map<String, Integer> def) { Map<String, Integer> map = getIntegerMap(path); return map.isEmpty() ? def : map; }
    // endregion
    
    // region Long
    /**
     * Returns the long at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The long at the specified path, or else null.
     */
    @Nullable Long getLong(@Nonnull String path);
    /**
     * Returns the long at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The long at the specified path, or else the default value.
     */
    default long getLong(@Nonnull String path, long def) { Long value = getLong(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the long-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The long-list at the specified path, or else an empty list.
     */
    @Nonnull List<Long> getLongList(@Nonnull String path);
    /**
     * Returns the long-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The long-list at the specified path, or else the default value.
     */
    @Nonnull default List<Long> getLongList(@Nonnull String path, @Nonnull List<Long> def) { List<Long> list = getLongList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Long> getLongMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getLong(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Long> getLongMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Long> def) {
        Map<K, Long> map = getLongMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Long> getLongMap(@Nonnull String path) { return getLongMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Long> getLongMap(@Nonnull String path, Map<String, Long> def) { Map<String, Long> map = getLongMap(path); return map.isEmpty() ? def : map; }
    // endregion

    // region Short
    /**
     * Returns the short at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The short at the specified path, or else null.
     */
    @Nullable Short getShort(@Nonnull String path);
    /**
     * Returns the short at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The short at the specified path, or else the default value.
     */
    default short getShort(@Nonnull String path, short def) { Short value = getShort(path); return Objects.isNull(value) ? def : value; }
    /**
     * Returns the short-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The short-list at the specified path, or else an empty list.
     */
    @Nonnull List<Short> getShortList(@Nonnull String path);
    /**
     * Returns the short-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The short-list at the specified path, or else the default value.
     */
    @Nonnull default List<Short> geShortList(@Nonnull String path, @Nonnull List<Short> def) { List<Short> list = getShortList(path); return list.isEmpty() ? def : list; }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Short> getShortMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer) {
        return getKeys(path, false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), getShort(sub)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K> Map<K, Short> getShortMap(@Nonnull String path, IObjectDeserializer<String, K> keyDeserializer, Map<K, Short> def) {
        Map<K, Short> map = getShortMap(path, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Short> getShortMap(@Nonnull String path) { return getShortMap(path, Serializer.STRING); }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default Map<String, Short> getShortMap(@Nonnull String path, Map<String, Short> def) { Map<String, Short> map = getShortMap(path); return map.isEmpty() ? def : map; }
    // endregion

    // region Serializable
    /**
     * Returns the boolean at the specified path, or else null.
     * @param path The path to get the value from.
     * @return The boolean at the specified path, or else null.
     */
    @SuppressWarnings("unchecked")
    @Nullable default <D extends IDataHolder, T extends ISerializable<D>> T getSerializable(@Nonnull String path, @Nonnull Class<T> type) {
        IDataHolder data = getDataSection(path);
        if(data == null) return null;

        try {
            Constructor<T> constructor = type.getConstructor(IDataHolder.class);
            constructor.setAccessible(true);
            return constructor.newInstance(data);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignore) { }

        try {
            Method method = type.getDeclaredMethod("deserialize", IDataHolder.class);
            method.setAccessible(true);
            return (T) method.invoke(null, data);
        } catch (ClassCastException | InvocationTargetException | NoSuchMethodException | IllegalAccessException ignore) { }

        return null;
    }
    /**
     * Returns the boolean at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The boolean at the specified path, or else the default value.
     */
    @Nonnull default <D extends IDataHolder, T extends ISerializable<D>> T getSerializable(@Nonnull String path, @Nonnull Class<T> type, @Nonnull T def) {
        T value = getSerializable(path, type);
        return Objects.isNull(value) ? def : value;
    }
    /**
     * Returns the boolean-list at the specified path, or else an empty list.
     * @param path The path to get the value from.
     * @return The boolean-list at the specified path, or else an empty list.
     */
    @Nonnull default  <D extends IDataHolder, T extends ISerializable<D>> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> type) {
        Map<Integer, T> map = new TreeMap<>(getSerializableMap(path, type, Serializer.INTEGER));
        return new ArrayList<>(map.values());
    }
    /**
     * Returns the boolean-list at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The boolean-list at the specified path, or else the default value.
     */
    @Nonnull default <D extends IDataHolder, T extends ISerializable<D>> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> type, @Nonnull List<T> def) {
        List<T> list = getSerializableList(path, type);
        return list.isEmpty() ? def : list;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K, D extends IDataHolder, T extends ISerializable<D>> Map<K, T> getSerializableMap(@Nonnull String path, @Nonnull Class<T> type, IObjectDeserializer<String, K> keyDeserializer) {
        IDataHolder data = getDataSection(path);
        if(data == null) return Collections.emptyMap();

        return data.getKeys(false).stream()
                .map((sub) -> new HashMap.SimpleImmutableEntry<>(keyDeserializer.deserialize(sub), data.getSerializable(sub, type)))
                .filter(entry -> Objects.nonNull(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param keyDeserializer The deserializer which turns strings into keys.
     * @param def The value to return if no value was found at the path.
     * @param <K> The type of the key.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <K, D extends IDataHolder, T extends ISerializable<D>> Map<K, T> getSerializableMap(@Nonnull String path, @Nonnull Class<T> type, IObjectDeserializer<String, K> keyDeserializer, Map<K, T> def) {
        Map<K, T> map = getSerializableMap(path, type, keyDeserializer);
        return map.isEmpty() ? def : map;
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <D extends IDataHolder, T extends ISerializable<D>> Map<String, T> getSerializableMap(@Nonnull String path, @Nonnull Class<T> type) {
        return getSerializableMap(path, type, Serializer.STRING);
    }
    /**
     * Returns the map at the specified path, or else the default value.
     * @param path The path to get the value from.
     * @param def The value to return if no value was found at the path.
     * @return The map at the specified path, or else the default value.
     */
    @Nonnull default <D extends IDataHolder, T extends ISerializable<D>> Map<String, T> getSerializableMap(@Nonnull String path, @Nonnull Class<T> type, Map<String, T> def) {
        Map<String, T> map = getSerializableMap(path, type); return map.isEmpty() ? def : map;
    }
    // endregion

    /**
     * Returns a {@link Dictionary} from the specified path.
     * @param path The path to get the {@link Dictionary} from.
     * @return A {@link Dictionary} from the specified path.
     */
    default Dictionary getDictionary(@Nonnull String path) {
        Dictionary dictionary = new Dictionary();
        getMap(path).forEach(dictionary::put);

        return dictionary;
    }

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
    Set<String> getKeys(@Nonnull String path, boolean deep);

    /**
     * Returns a DataHolder from the specified path.
     * @param path The path to get the DataHolder from.
     * @return A DataHolder from the specified path.
     */
    IDataHolder getDataSection(@Nonnull String path);
    
    // endregion
    
    // region Setters
    
    // region String
    /**
     * Sets the specified string to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(@Nonnull String path, @Nonnull String value);
    /**
     * Sets the specified string-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setStringList(@Nonnull String path, @Nonnull List<String> list);
    /**
     * Sets the specified string-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     * @param keySerializer The serializer to serialize the key with.
     */
    default <V> void setStringMap(@Nonnull String path, @Nonnull Map<V, String> map, @Nonnull IObjectSerializer<V, String> keySerializer) {
        delete(path);
        map.forEach((child, value) -> set(path + getPathDivider() + keySerializer.serialize(child), value));
    }
    /**
     * Sets the specified string-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setStringMap(@Nonnull String path, @Nonnull Map<String, String> map) {
        setStringMap(path, map, Serializer.STRING);
    }
    // endregion

    // region Character
    /**
     * Sets the specified character to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(@Nonnull String path, char value);
    /**
     * Sets the specified character-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setCharacterList(@Nonnull String path, @Nonnull List<Character> list);
    /**
     * Sets the specified character-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     * @param keySerializer The serializer to serialize the key with.
     */
    default <V> void setCharacterMap(@Nonnull String path, @Nonnull Map<V, Character> map, @Nonnull IObjectSerializer<V, String> keySerializer) {
        delete(path);
        map.forEach((child, value) -> set(path + getPathDivider() + keySerializer.serialize(child), value));
    }
    /**
     * Sets the specified character-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setCharacterMap(@Nonnull String path, @Nonnull Map<String, Character> map) {
        setCharacterMap(path, map, Serializer.STRING);
    }
    // endregion

    // region Boolean
    /**
     * Sets the specified boolean to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(@Nonnull String path, boolean value);
    /**
     * Sets the specified boolean-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    void setBooleanList(@Nonnull String path, @Nonnull List<Boolean> list);
    /**
     * Sets the specified boolean-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     * @param keySerializer The serializer to serialize the key with.
     */
    default <V> void setBooleanMap(@Nonnull String path, @Nonnull Map<V, Boolean> map, @Nonnull IObjectSerializer<V, String> keySerializer) {
        delete(path);
        map.forEach((child, value) -> set(path + getPathDivider() + keySerializer.serialize(child), value));
    }
    /**
     * Sets the specified boolean-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default void setBooleanMap(@Nonnull String path, @Nonnull Map<String, Boolean> map) {
        setBooleanMap(path, map, Serializer.STRING);
    }
    // endregion

    // region Numbers
    /**
     * Sets the specified number to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    void set(@Nonnull String path, @Nonnull Number value);
    /**
     * Sets the specified number-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    <N extends Number> void setNumberList(@Nonnull String path, @Nonnull List<N> list);
    /**
     * Sets the specified number-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     * @param keySerializer The serializer to serialize the key with.
     */
    default <N extends Number, V> void setNumberMap(@Nonnull String path, @Nonnull Map<V, N> map, @Nonnull IObjectSerializer<V, String> keySerializer) {
        delete(path);
        map.forEach((child, value) -> set(path + getPathDivider() + keySerializer.serialize(child), value));
    }
    /**
     * Sets the specified number-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default <N extends Number> void setNumberMap(@Nonnull String path, @Nonnull Map<String, N> map) {
        setNumberMap(path, map, Serializer.STRING);
    }
    // endregion

    // region Serializable
    /**
     * Sets the specified number to a specified path.
     * @param path The path to set the value to.
     * @param value The value to set to the path.
     */
    default <T extends ISerializable<IDataHolder>> void set(@Nonnull String path, @Nonnull T value) {
        IDataHolder data = createEmptySection();
        setDataSection(path, value.serialize(data));
    }
    /**
     * Sets the specified number-list to a specified path.
     * @param path The path to set the value to.
     * @param list The value to set to the path.
     */
    default <T extends ISerializable<IDataHolder>> void setSerializableList(@Nonnull String path, @Nonnull List<T> list) {
        Map<Integer, ISerializable<IDataHolder>> map = new HashMap<>();
        
        for(int i = 0; i < list.size(); i++)
            map.put(i, list.get(i));
        
        setSerializableMap(path, map, Serializer.INTEGER);
    }
    /**
     * Sets the specified number-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     * @param keySerializer The serializer to serialize the key with.
     */
    default <V, T extends ISerializable<IDataHolder>> void setSerializableMap(@Nonnull String path, @Nonnull Map<V, T> map, @Nonnull IObjectSerializer<V, String> keySerializer) {
        map.forEach((child, value) -> set(path + getPathDivider() + keySerializer.serialize(child), value));
    }
    /**
     * Sets the specified number-map to a specified path.
     * @param path The path to set the value to.
     * @param map The value to set to the path.
     */
    default <T extends ISerializable<IDataHolder>> void setSerializableMap(@Nonnull String path, @Nonnull Map<String, T> map) {
        setSerializableMap(path, map, Serializer.STRING);
    }
    // endregion

    /**
     * Sets a DataHolder to a specified path.
     * @param path The path to set the DataHolder to.
     * @param data The DataHolder to set to the specified path.
     */
    void setDataSection(@Nonnull String path, @Nonnull IDataHolder data);
    
    // endregion

    // endregion

    // region Operations

    /**
     * Deletes a specified path.
     * @param path The path to be deleted.
     */
    void delete(@Nonnull String path);

    /**
     * Creates an empty DataHolder at the given path, and returns the created DataHolder.
     * @return The created DataHolder.
     */
    IDataHolder createEmptySection();

    // endregion

    @Nonnull default String getPathDivider() {
        return ".";
    }

    @Nonnull default String getPathSplitterRegex() {
        return "\\.";
    }
    
    /**
     * Gets a value from a path, using a deserializer. If the value is null, return the default value.
     * @param path          Path to get the value from.
     * @param deserializer  Deserializer to deserialize the value with.
     * @param def           The default value to return if null.
     * @param <V>           Type of the value.
     * @return              Value at the given path, or the default value if null.
     */
    @Deprecated
    @Nonnull default <V> V getOrDefault(@Nonnull String path, @Nonnull IObjectDeserializer<String, V> deserializer, @Nonnull V def) {
        return get(path, deserializer, def);
    }

    /**
     * Gets a value from a path, using a deserializer. If the value is null, return the default value.
     * @param path          Path to get the value from.
     * @param deserializer  Deserializer to deserialize the value with.
     * @param def           The default value to return if null.
     * @param <V>           Type of the value.
     * @return              Value at the given path, or the default value if null.
     */
    @Nonnull default <V> V get(@Nonnull String path, @Nonnull IObjectDeserializer<String, V> deserializer, @Nonnull V def) {
        V value = get(path, deserializer);
        return Objects.isNull(value) ? def : value;
    }

    /**
     * Gets a value from a path, using a deserializer.
     * @param path         Path to get the value from.
     * @param deserializer Deserializer to deserialize the value with.
     * @param <V>          Type of the value.
     * @return Value at the given path.
     */
    @Nullable default <V> V get(@Nonnull String path, @Nonnull IObjectDeserializer<String, V> deserializer) {
        String value = getString(path);
        try {
            return deserializer.deserialize(value);
        } catch (Exception exception) { return null; }
    }

    /**
     * Sets a value to a specified path, using a serializer.
     * @param path       Path to set the value to.
     * @param value      Value to be set to the path.
     * @param serializer Serializer to be used to serialize the value.
     * @param <V>        Type of the value.
     */
    default <V> void set(@Nonnull String path, @Nonnull V value, @Nonnull IObjectSerializer<V, String> serializer) {
        set(path, serializer.serialize(value));
    }

    /**
     * Gets a list from a path, using a deserializer.
     * @param path         Path to get the list from.
     * @param deserializer Deserializer to deserialize the list items with.
     * @param <V>          Type of the list items.
     * @return List at the given path.
     */
    @Nonnull default <V> List<V> getList(@Nonnull String path, @Nonnull IObjectDeserializer<String, V> deserializer) {
        List<String> list = getStringList(path);
        try {
            return list.stream().map(deserializer::deserialize).collect(Collectors.toList());
        } catch (Exception exception) { return Collections.emptyList(); }
    }

    /**
     * Sets a list to a specified path, using a serializer.
     * @param path       Path to set the list to.
     * @param list       List to be set to the path.
     * @param serializer Serializer to be used to serialize the list items.
     * @param <V>        Type of the list items.
     */
    default <V> void setList(@Nonnull String path, @Nonnull List<V> list, @Nonnull IObjectSerializer<V, String> serializer) {
        List<String> stringList = list.stream().map(serializer::serialize).collect(Collectors.toList());
        setStringList(path, stringList);
    }

    /**
     * Returns a {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     * @param path The path to get the {@link Map} from.
     * @return A {@link Map} containing the keys and values, in string format, of a {@link ConfigurationSection} at the specified path.
     */
    @Nonnull default Map<String, String> getMap(@Nonnull String path) {
        return getMap(path, Serializer.STRING);
    }

    /**
     * Returns the same as {@link #getMap(String map)}, but converts the value with a (de)serializer.
     * @param valueDeserializer The deserializer to deserialize the value from string.
     * @param path              The path to get the {@link Map} from.
     * @param <V>               The type to convert the value to.
     * @return The same as {@link #getMap(String map)}, but converts the value with a deserializer.
     */
    @Nonnull default <V> Map<String, V> getMap(@Nonnull String path, @Nonnull IObjectDeserializer<String, V> valueDeserializer) {
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
    @Nonnull default <K, V> Map<K, V> getMap(@Nonnull String path, @Nonnull IObjectDeserializer<String, K> keyDeserializer, @Nonnull IObjectDeserializer<String, V> valueDeserializer) {
        HashMap<K, V> map = new HashMap<>();
        Set<String> keys = getDataSection(path).getKeys(false);

        for (String stringKey : keys) {
            String stringValue = getString(path + getPathDivider() + stringKey);

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
    default void setMap(@Nonnull String path, @Nonnull Map<String, String> values) {
        setMap(path, values, Serializer.STRING);
    }

    /**
     * Sets a map to the specified path, and uses the serializers to convert the key and value.
     * @param path            Path to set the map to.
     * @param map             Map to be set to the path.
     * @param valueSerializer Serializer used to serialize the value.
     * @param <V>             Type of the value.
     */
    default <V> void setMap(@Nonnull String path, @Nonnull Map<String, V> map, @Nonnull IObjectSerializer<V, String> valueSerializer) {
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
    default <K, V> void setMap(@Nonnull String path, @Nonnull Map<K, V> map, @Nonnull IObjectSerializer<K, String> keySerializer, @Nonnull IObjectSerializer<V, String> valueSerializer) {
        delete(path);

        for (Map.Entry<K, V> entry : map.entrySet()) {
            String key = keySerializer.serialize(entry.getKey());
            String value = valueSerializer.serialize(entry.getValue());
            set(path + getPathDivider() + key, value);
        }
    }
}