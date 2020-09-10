package nl.tabuu.tabuucore.configuration.holder;

import nl.tabuu.tabuucore.configuration.IDataHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class YamlDataHolder extends AbstractDataHolder<Map<String, Object>, Object> {

    public YamlDataHolder() {
        super();
    }

    public YamlDataHolder(Map<String, Object> root) {
        super(root);
    }

    @Nonnull
    @Override
    protected BiFunction<Map<String, Object>, String, Object> getChildFromParentWithKeyFunction() {
        return Map::get;
    }

    @Nonnull
    @Override
    protected BiConsumer<Map<String, Object>, String> getDeleteChildFromParentWithKeyFunction() {
        return Map::remove;
    }

    @Override
    protected boolean isElementIterable(Object element) {
        return element instanceof Iterable;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected Iterable<Object> getElementAsIterable(@Nonnull Object element) {
        return isElementIterable(element) ? (Iterable<Object>) element : null;
    }

    @Override
    protected boolean isElementParent(@Nonnull Object element) {
        return element instanceof Map;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    protected Map<String, Object> getElementAsParent(@Nonnull Object element) {
        return isElementParent(element) ? (Map<String, Object>) element : null;
    }

    @Override
    protected boolean parentContains(@Nonnull Map<String, Object> parent, @Nonnull String key) {
        return parent.containsKey(key);
    }

    @Nonnull
    @Override
    protected Map<String, Object> createEmptyParent() {
        return new HashMap<>();
    }

    @Nonnull
    @Override
    protected IDataHolder createDataHolder(Map<String, Object> root) {
        return new YamlDataHolder(root);
    }

    @Override
    protected void addChild(@Nonnull Map<String, Object> parent, @Nonnull String key, Object element) {
        parent.put(key, element);
    }

    @Nonnull
    @Override
    protected <T> Object iterableToElement(Iterable<T> iterable, Function<T, Object> valueToElementFunction) {
        List<T> list = new ArrayList<T>();
        for(T item : iterable)
            list.add(item);

        return list;
    }

    @Nonnull
    @Override
    protected Set<Map.Entry<String, Object>> getChildren(@Nonnull Map<String, Object> parent) {
        return getRoot().entrySet();
    }

    @Nonnull
    @Override
    protected Function<String, Object> getStringToElementFunction() {
        return (value) -> value;
    }

    @Nonnull
    @Override
    protected Function<Character, Object> getCharacterToElementFunction() {
        return (value) -> value;
    }

    @Nonnull
    @Override
    protected Function<Boolean, Object> getBooleanToElementFunction() {
        return (value) -> value;
    }

    @Nonnull
    @Override
    protected <N extends Number> Function<N, Object> getNumberToElementFunction() {
        return (value) -> value;
    }

    @Nonnull
    protected <T> Function<Object, T> getElementToTypeFunction(Class<T> type) {
        return getElementToTypeOrNull(type::cast);
    }

    @Nonnull
    @Override
    protected Function<Object, String> getElementToStringFunction() {
        return getElementToTypeFunction(String.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Character> getElementToCharacterFunction() {
        return getElementToTypeFunction(Character.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Boolean> getElementToBooleanFunction() {
        return getElementToTypeFunction(Boolean.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Byte> getElementToByteFunction() {
        return getElementToTypeFunction(Byte.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Double> getElementToDoubleFunction() {
        return getElementToTypeFunction(Double.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Float> getElementToFloatFunction() {
        return getElementToTypeFunction(Float.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Integer> getElementToIntegerFunction() {
        return getElementToTypeFunction(Integer.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Long> getElementToLongFunction() {
        return getElementToTypeFunction(Long.class);
    }

    @Nonnull
    @Override
    protected Function<Object, Short> getElementToShortFunction() {
        return getElementToTypeFunction(Short.class);
    }
}