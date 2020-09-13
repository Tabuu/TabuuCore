package nl.tabuu.tabuucore.configuration.holder;

import nl.tabuu.tabuucore.configuration.IDataHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class AbstractDataHolder<P extends C, C> implements IDataHolder {

    private P _root;

    public AbstractDataHolder(P root) {
        _root = root;
    }

    public AbstractDataHolder() {
        _root = createEmptyParent();
    }

    // region Utilities

    @Nonnull protected P getRoot() {
        return _root;
    }

    protected void setRoot(P root) {
        _root = root;
    }

    @Nonnull protected abstract BiFunction<P, String, C> getChildFromParentWithKeyFunction();

    @Nonnull protected abstract BiConsumer<P, String> getDeleteChildFromParentWithKeyFunction();

    protected boolean isElementIterable(C element) {
        return getElementAsIterable(element) != null;
    }

    @Nullable protected abstract Iterable<C> getElementAsIterable(@Nonnull C element);

    protected boolean isElementParent(@Nonnull C element) {
        return getElementAsParent(element) != null;
    }

    @Nullable protected abstract P getElementAsParent(@Nonnull C element);

    protected abstract boolean parentContains(@Nonnull P parent, @Nonnull String key);

    @Nonnull protected abstract P createEmptyParent();

    @Nonnull protected abstract IDataHolder createDataHolder(P root);

    protected abstract void addChild(@Nonnull P parent, @Nonnull String key, C element);

    protected void setElement(@Nonnull String path, C element) {
        if(path.isEmpty() && isElementParent(element)) {
            P root = getElementAsParent(element);
            setRoot(root);
        } else forParentWithKey(path, (parent, key) -> addChild(parent, key, element));
    }

    protected <T> void setValue(@Nonnull String path, T value, Function<T, C> valueToElementFunction) {
        setElement(path, valueToElementFunction.apply(value));
    }

    protected <T> void setValue(@Nonnull String path, List<T> value, Function<T, C> valueToElementFunction) {
        setElement(path, iterableToElement(value, valueToElementFunction));
    }

    @Nonnull protected abstract <T> C iterableToElement(Iterable<T> iterable, Function<T, C> valueToElementFunction);

    @Nullable protected C getElement(@Nonnull String path) {
        Map.Entry<P, String> parentAndKey = getParentAndKey(path);
        if(parentAndKey.getValue().isEmpty()) return parentAndKey.getKey();

        return getChildFromParentWithKeyFunction().apply(parentAndKey.getKey(), parentAndKey.getValue());
    }

    @Nonnull protected abstract Set<Map.Entry<String, C>> getChildren(@Nonnull P parent);

    @Nullable protected <T> T getValue(@Nonnull String path, Function<C, T> function) {
        try {
            return function.apply(getElement(path));
        } catch (Exception exception) {
            return null;
        }
    }

    @Nonnull protected <T> List<T> getListValue(String path, Function<C, T> function) {
        C child = getElement(path);
        if(child == null || !isElementIterable(child)) return Collections.emptyList();

        Iterable<C> iterable = getElementAsIterable(child);
        if(iterable == null) return Collections.emptyList();

        List<T> list = new ArrayList<>();

        for(C element : iterable) {
            try {
                list.add(function.apply(element));
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    @Nonnull protected Map.Entry<P, String> getParentAndKey(@Nonnull String path) {
        String[] parts = path.split(getPathSplitterRegex());
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        P parent = getRoot();
        String key;

        if(parts.length == 0) return new HashMap.SimpleImmutableEntry<>(parent, "");
        else if(parts.length == 1) key = parts[0];
        else key = parts[parts.length - 1];

        for(int i = 0; i < parts.length - 1; i++) {
            String part = parts[i];
            if(!parentContains(parent, part)) addChild(parent, part, createEmptyParent());
            C element = getChildFromParentWithKeyFunction().apply(parent, part);

            if(element == null || !isElementParent(element)) return new HashMap.SimpleImmutableEntry<>(parent, key);
            parent = getElementAsParent(element);
        }

        return new HashMap.SimpleImmutableEntry<>(parent, key);
    }

    protected void forParentWithKey(@Nonnull String path, BiConsumer<P, String> parentAndKeyConsumer) {
        Map.Entry<P, String> parentAndKey = getParentAndKey(path);
        parentAndKeyConsumer.accept(parentAndKey.getKey(), parentAndKey.getValue());
    }

    // endregion
    
    // region Transformers

    protected <T> Function<C, T> getElementToTypeOrNull(Function<C, T> childToTypeFunction) {
        return (element) -> {
            try {
                return childToTypeFunction.apply(element);
            } catch (Exception exception) {
                return null;
            }
        };
    }

    @Nonnull protected abstract Function<String, C> getStringToElementFunction();
    @Nonnull protected abstract Function<Character, C> getCharacterToElementFunction();
    @Nonnull protected abstract Function<Boolean, C> getBooleanToElementFunction();
    @Nonnull protected abstract <N extends Number> Function<N, C> getNumberToElementFunction();
    
    @Nonnull protected abstract Function<C, String> getElementToStringFunction();
    @Nonnull protected abstract Function<C, Character> getElementToCharacterFunction();
    @Nonnull protected abstract Function<C, Boolean> getElementToBooleanFunction();
    @Nonnull protected abstract Function<C, Byte> getElementToByteFunction();
    @Nonnull protected abstract Function<C, Double> getElementToDoubleFunction();
    @Nonnull protected abstract Function<C, Float> getElementToFloatFunction();
    @Nonnull protected abstract Function<C, Integer> getElementToIntegerFunction();
    @Nonnull protected abstract Function<C, Long> getElementToLongFunction();
    @Nonnull protected abstract Function<C, Short> getElementToShortFunction();

    // endregion

    @Override
    @Nonnull public IDataHolder createEmptySection() {
        return createDataHolder(createEmptyParent());
    }

    @Override
    public void delete(@Nonnull String path) {
        if(path.isEmpty()) setRoot(createEmptyParent());
        else forParentWithKey(path, (parent, key) -> getDeleteChildFromParentWithKeyFunction().accept(parent, key));
    }

    // region Getters

    @Override
    public IDataHolder getDataSection(@Nonnull String path) {
        C element = getElement(path);
        if(element == null || !isElementParent(element)) return null;

        P root = getElementAsParent(element);
        return createDataHolder(root);
    }

    private Set<String> getKeys(Set<String> keys, String path, P object, boolean deep) {
        if(object == null) return keys;

        for(Map.Entry<String, C> entry : getChildren(object)) {
            String name = entry.getKey();
            String newPath = path + (path.isEmpty() ? "" : getPathDivider()) + name;
            C element = entry.getValue();
            keys.add(newPath);

            if(deep && isElementParent(element))
                getKeys(keys, newPath, getElementAsParent(element), true);
        }

        return keys;
    }

    @Override
    public Set<String> getKeys(@Nonnull String path, boolean deep) {
        C element;

        if(path.isEmpty()) element = getRoot();
        else element = getElement(path);

        return getKeys(new HashSet<>(), path, element != null && isElementParent(element) ? getElementAsParent(element) : null, deep);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    @Override
    public String getString(@Nonnull String path) {
        return getValue(path, getElementToStringFunction());
    }

    @Nonnull
    @Override
    public List<String> getStringList(@Nonnull String path) {
        return getListValue(path, getElementToStringFunction());
    }

    @Nullable
    @Override
    public Character getCharacter(@Nonnull String path) {
        return getValue(path, getElementToCharacterFunction());
    }

    @Nonnull
    @Override
    public List<Character> getCharacterList(@Nonnull String path) {
        return getListValue(path, getElementToCharacterFunction());
    }

    @Nullable
    @Override
    public Boolean getBoolean(@Nonnull String path) {
        return getValue(path, getElementToBooleanFunction());
    }

    @Nonnull
    @Override
    public List<Boolean> getBooleanList(@Nonnull String path) {
        return getListValue(path, getElementToBooleanFunction());
    }

    @Nullable
    @Override
    public Byte getByte(@Nonnull String path) {
        return getValue(path, getElementToByteFunction());
    }

    @Nonnull
    @Override
    public List<Byte> getByteList(@Nonnull String path) {
        return getListValue(path, getElementToByteFunction());
    }

    @Nullable
    @Override
    public Double getDouble(@Nonnull String path) {
        return getValue(path, getElementToDoubleFunction());
    }

    @Nonnull
    @Override
    public List<Double> getDoubleList(@Nonnull String path) {
        return getListValue(path, getElementToDoubleFunction());
    }

    @Nullable
    @Override
    public Float getFloat(@Nonnull String path) {
        return getValue(path, getElementToFloatFunction());
    }

    @Nonnull
    @Override
    public List<Float> getFloatList(@Nonnull String path) {
        return getListValue(path, getElementToFloatFunction());
    }

    @Nullable
    @Override
    public Integer getInteger(@Nonnull String path) {
        return getValue(path, getElementToIntegerFunction());
    }

    @Nonnull
    @Override
    public List<Integer> getIntegerList(@Nonnull String path) {
        return getListValue(path, getElementToIntegerFunction());
    }

    @Nullable
    @Override
    public Long getLong(@Nonnull String path) {
        return getValue(path, getElementToLongFunction());
    }

    @Nonnull
    @Override
    public List<Long> getLongList(@Nonnull String path) {
        return getListValue(path, getElementToLongFunction());
    }

    @Nullable
    @Override
    public Short getShort(@Nonnull String path) {
        return getValue(path, getElementToShortFunction());
    }

    @Nonnull
    @Override
    public List<Short> getShortList(@Nonnull String path) {
        return getListValue(path, getElementToShortFunction());
    }

    // endregion

    // region Setters

    @Override
    @SuppressWarnings("unchecked")
    public void setDataSection(@Nonnull String path, @Nonnull IDataHolder data) {
        if(!data.getClass().isInstance(this)) return;
        delete(path);

        try {
            AbstractDataHolder<P, C> holder = (AbstractDataHolder<P, C>) data;
            setElement(path, holder.getRoot());
        } catch (ClassCastException ignore) { }
    }

    @Override
    public void set(@Nonnull String path, @Nonnull String value) {
        setValue(path, value, getStringToElementFunction());
    }

    @Override
    public void setStringList(@Nonnull String path, @Nonnull List<String> list) {
        setValue(path, list, getStringToElementFunction());
    }

    @Override
    public void set(@Nonnull String path, char value) {
        setValue(path, value, getCharacterToElementFunction());
    }

    @Override
    public void setCharacterList(@Nonnull String path, @Nonnull List<Character> list) {
        setValue(path, list, getCharacterToElementFunction());
    }

    @Override
    public void set(@Nonnull String path, boolean value) {
        setValue(path, value, getBooleanToElementFunction());
    }

    @Override
    public void setBooleanList(@Nonnull String path, @Nonnull List<Boolean> list) {
        setValue(path, list, getBooleanToElementFunction());
    }

    @Override
    public void set(@Nonnull String path, @Nonnull Number value) {
        setValue(path, value, getNumberToElementFunction());
    }

    @Override
    public <N extends Number> void setNumberList(@Nonnull String path, @Nonnull List<N> list) {
        setValue(path, list, getNumberToElementFunction());
    }

    // endregion
}