package nl.tabuu.tabuucore.configuration.holder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import nl.tabuu.tabuucore.configuration.IDataHolder;
import nl.tabuu.tabuucore.serialization.ISerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JsonDataHolder extends AbstractDataHolder<JsonObject, JsonElement> {

    public JsonDataHolder() {
        super();
    }

    public JsonDataHolder(JsonObject root) {
        super(root);
    }

    @Override
    public <T extends ISerializable<IDataHolder>> void setSerializableList(@Nonnull String path, @Nonnull List<T> list) {
        JsonArray array = new JsonArray();
        for(T item : list) {
            JsonObject object = createEmptyParent();
            IDataHolder data = createDataHolder(object);
            item.serialize(data);
            array.add(object);
        }

        setElement(path, array);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <D extends IDataHolder, T extends ISerializable<D>> List<T> getSerializableList(@Nonnull String path, @Nonnull Class<T> type) {
        return getListValue(path, (element) -> {

            if(!element.isJsonObject()) return null;
            JsonObject object = element.getAsJsonObject();

            try {
                Method method = type.getDeclaredMethod("deserialize", IDataHolder.class);
                method.setAccessible(true);
                return (T) method.invoke(null, createDataHolder(object));
            } catch (ClassCastException | NoSuchMethodException | IllegalAccessException ignore) { } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
            }

            try {
                Constructor<T> constructor = type.getDeclaredConstructor(IDataHolder.class);
                constructor.setAccessible(true);
                return constructor.newInstance(createDataHolder(object));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException ignore) { } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
            }

            return null;

        });
    }

    @Nonnull
    @Override
    protected BiFunction<JsonObject, String, JsonElement> getChildFromParentWithKeyFunction() {
        return JsonObject::get;
    }

    @Nonnull
    @Override
    protected BiConsumer<JsonObject, String> getDeleteChildFromParentWithKeyFunction() {
        return JsonObject::remove;
    }

    @Override
    protected boolean isElementIterable(JsonElement element) {
        return element.isJsonArray();
    }

    @Nullable
    @Override
    protected Iterable<JsonElement> getElementAsIterable(@Nonnull JsonElement element) {
        return isElementIterable(element) ? element.getAsJsonArray() : null;
    }

    @Override
    protected boolean isElementParent(@Nonnull JsonElement element) {
        return element.isJsonObject();
    }

    @Nullable
    @Override
    protected JsonObject getElementAsParent(@Nonnull JsonElement element) {
        return isElementParent(element) ? element.getAsJsonObject() : null;
    }

    @Override
    protected boolean parentContains(@Nonnull JsonObject parent, @Nonnull String key) {
        return parent.has(key);
    }

    @Nonnull
    @Override
    protected JsonObject createEmptyParent() {
        return new JsonObject();
    }

    @Nonnull
    @Override
    protected IDataHolder createDataHolder(JsonObject root) {
        return new JsonDataHolder(root);
    }

    @Override
    protected void addChild(@Nonnull JsonObject parent, @Nonnull String key, JsonElement element) {
        parent.add(key, element);
    }

    @Nonnull
    @Override
    protected <T> JsonElement iterableToElement(Iterable<T> iterable, Function<T, JsonElement> valueToElementFunction) {
        JsonArray array = new JsonArray();
        for(T item : iterable)
            array.add(valueToElementFunction.apply(item));

        return array;
    }

    @Nonnull
    @Override
    protected Set<Map.Entry<String, JsonElement>> getChildren(@Nonnull JsonObject parent) {
        return parent.entrySet();
    }

    @Nonnull
    @Override
    protected Function<String, JsonElement> getStringToElementFunction() {
        return JsonPrimitive::new;
    }

    @Nonnull
    @Override
    protected Function<Character, JsonElement> getCharacterToElementFunction() {
        return JsonPrimitive::new;
    }

    @Nonnull
    @Override
    protected Function<Boolean, JsonElement> getBooleanToElementFunction() {
        return JsonPrimitive::new;
    }

    @Nonnull
    @Override
    protected <N extends Number> Function<N, JsonElement> getNumberToElementFunction() {
        return JsonPrimitive::new;
    }

    @Nonnull
    @Override
    protected Function<JsonElement, String> getElementToStringFunction() {
        return getElementToTypeOrNull(JsonElement::getAsString);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Character> getElementToCharacterFunction() {
        return getElementToTypeOrNull(JsonElement::getAsCharacter);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Boolean> getElementToBooleanFunction() {
        return getElementToTypeOrNull(JsonElement::getAsBoolean);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Byte> getElementToByteFunction() {
        return getElementToTypeOrNull(JsonElement::getAsByte);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Double> getElementToDoubleFunction() {
        return getElementToTypeOrNull(JsonElement::getAsDouble);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Float> getElementToFloatFunction() {
        return getElementToTypeOrNull(JsonElement::getAsFloat);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Integer> getElementToIntegerFunction() {
        return getElementToTypeOrNull(JsonElement::getAsInt);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Long> getElementToLongFunction() {
        return getElementToTypeOrNull(JsonElement::getAsLong);
    }

    @Nonnull
    @Override
    protected Function<JsonElement, Short> getElementToShortFunction() {
        return getElementToTypeOrNull(JsonElement::getAsShort);
    }
}