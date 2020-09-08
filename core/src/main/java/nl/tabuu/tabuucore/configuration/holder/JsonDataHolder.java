package nl.tabuu.tabuucore.configuration.holder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import nl.tabuu.tabuucore.configuration.IDataHolder;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JsonDataHolder implements IDataHolder {

    protected JsonObject _root;

    public JsonDataHolder() {
        _root = new JsonObject();
    }

    public JsonDataHolder(JsonObject root) {
        _root = root;
    }

    // region Json Utilities
    private JsonElement getJsonTarget(String path, BiFunction<JsonObject, String, JsonElement> parentPropertyFunction) {
        String[] parts = path.split("\\.");
        parts = Arrays.stream(parts).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if(parts.length < 1) return _root;
        else if(parts.length < 2) return parentPropertyFunction.apply(_root, parts[0]);

        String propertyName = parts[parts.length - 1];
        parts = Arrays.copyOfRange(parts, 0, parts.length - 1);

        JsonObject parent = _root;

        for(String part : parts) {
            if(!parent.has(part)) parent.add(part, new JsonObject());
            else if(!parent.get(part).isJsonObject()) return null;

            parent = parent.getAsJsonObject(part);
        }

        return parentPropertyFunction.apply(parent, propertyName);
    }

    private void forJsonTarget(String path, BiConsumer<JsonObject, String> parentPropertyConsumer) {
        getJsonTarget(path, (parent, property) -> {
            parentPropertyConsumer.accept(parent, property);
            return null;
        });
    }

    private JsonElement getJsonElement(String path) {
        return getJsonTarget(path, JsonObject::get);
    }

    private <T> T getJsonElementValue(String path, Function<JsonElement, T> function) {
        try {
            return function.apply(getJsonElement(path));
        } catch (ClassCastException | IllegalStateException exception) {
            return null;
        }
    }

    private <T> List<T> getJsonArrayValues(String path, Function<JsonElement, T> function) {
        JsonElement element = getJsonElement(path);
        if(element == null || !element.isJsonArray()) return null;

        JsonArray array = element.getAsJsonArray();
        List<T> list = new ArrayList<>();

        for(JsonElement item : array) {
            try {
                list.add(function.apply(item));
            } catch (ClassCastException | IllegalStateException ignore) { }
        }

        return list;
    }

    // endregion

    @Override
    public void delete(String path) {
        if(path.isEmpty()) _root = new JsonObject();
        else getJsonTarget(path, JsonObject::remove);
    }

    @Override
    public IDataHolder createSection(String path) {
        JsonObject child = new JsonObject();
        forJsonTarget(path, (parent, property) -> parent.add(property, child));

        return new JsonDataHolder(child);
    }

    @Override
    public IDataHolder getDataSection(String path) {
        JsonElement element = getJsonTarget(path, JsonObject::get);
        if(element == null || !element.isJsonObject()) return null;
        else return new JsonDataHolder(element.getAsJsonObject());
    }

    @Override
    public void setDataSection(String path, IDataHolder data) {
        if(!(data instanceof JsonDataHolder))
            throw new IllegalArgumentException("DataSection must have the same type as parent.");

        JsonDataHolder section = (JsonDataHolder) data;
        if(path.isEmpty()) _root = section._root;
        else forJsonTarget(path, (parent, property) -> parent.add(property, section._root));
    }

    private Set<String> getKeys(Set<String> keys, String path, JsonObject object, boolean deep) {
        if(object == null) return keys;

        for(Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String name = entry.getKey();
            String newPath = path + (path.isEmpty() ? "" : ".") + name;
            JsonElement element = entry.getValue();
            keys.add(newPath);

            if(deep && element.isJsonObject())
                getKeys(keys, newPath, element.getAsJsonObject(), true);
        }

        return keys;
    }

    @Override
    public Set<String> getKeys(String path, boolean deep) {
        JsonElement element;

        if(path.isEmpty()) element = _root;
        else element = getJsonElement(path);

        return getKeys(new HashSet<>(), path, element != null && element.isJsonObject() ? element.getAsJsonObject() : null, deep);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return getKeys("", deep);
    }

    @Override
    public String getString(String path) {
        return getJsonElementValue(path, JsonElement::getAsString);
    }

    @Override
    public List<String> getStringList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsString);
    }

    @Override
    public void set(String path, String value) {
        forJsonTarget(path, (parent, property) -> parent.addProperty(property, value));
    }

    @Override
    public void setStringList(String path, List<String> list) {
        JsonArray array = new JsonArray();
        list.forEach(array::add);

        forJsonTarget(path, (parent, property) -> parent.add(property, array));
    }

    @Override
    public Boolean getBoolean(String path) {
        return getJsonElementValue(path, JsonElement::getAsBoolean);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsBoolean);
    }

    @Override
    public void set(String path, boolean value) {
        forJsonTarget(path, (parent, property) -> parent.addProperty(property, value));
    }

    @Override
    public void setBooleanList(String path, List<Boolean> list) {
        JsonArray array = new JsonArray();
        list.forEach(array::add);

        forJsonTarget(path, (parent, property) -> parent.add(property, array));
    }

    @Override
    public Character getCharacter(String path) {
        return getJsonElementValue(path, JsonElement::getAsCharacter);
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsCharacter);
    }

    @Override
    public void set(String path, char value) {
        forJsonTarget(path, (parent, property) -> parent.addProperty(property, value));
    }

    @Override
    public void setCharacterList(String path, List<Character> list) {
        JsonArray array = new JsonArray();
        list.forEach(array::add);

        forJsonTarget(path, (parent, property) -> parent.add(property, array));
    }

    @Override
    public Double getDouble(String path) {
        return getJsonElementValue(path, JsonElement::getAsDouble);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsDouble);
    }

    @Override
    public Long getLong(String path) {
        return getJsonElementValue(path, JsonElement::getAsLong);
    }

    @Override
    public List<Long> getLongList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsLong);
    }

    @Override
    public Integer getInteger(String path) {
        return getJsonElementValue(path, JsonElement::getAsInt);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsInt);
    }

    @Override
    public Float getFloat(String path) {
        return getJsonElementValue(path, JsonElement::getAsFloat);
    }

    @Override
    public List<Float> getFloatList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsFloat);
    }

    @Override
    public Byte getByte(String path) {
        return getJsonElementValue(path, JsonElement::getAsByte);
    }

    @Override
    public List<Byte> getByteList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsByte);
    }

    @Override
    public Short getShort(String path) {
        return getJsonElementValue(path, JsonElement::getAsShort);
    }

    @Override
    public List<Short> getShortList(String path) {
        return getJsonArrayValues(path, JsonElement::getAsShort);
    }

    @Override
    public void set(String path, Number value) {
        forJsonTarget(path, (parent, property) -> parent.addProperty(property, value));
    }

    @Override
    public void setNumberList(String path, List<Number> list) {
        JsonArray array = new JsonArray();
        list.forEach(array::add);

        forJsonTarget(path, (parent, property) -> parent.add(property, array));
    }
}
