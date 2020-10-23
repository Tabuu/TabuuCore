package nl.tabuu.tabuucore.serialization.string;

import nl.tabuu.tabuucore.serialization.ISerializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStringSerializer<T> implements ISerializer<T, String> {

    @Override
    public String serializeArray(T... array) {
        if(Objects.isNull(array)) return null;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            builder.append(serialize(array[i]));

            if (i < array.length - 1)
                builder.append(" ");
        }

        return builder.toString();
    }

    @Override
    public T[] deserializeArray(String string) {
        if(Objects.isNull(string)) return null;

        String[] strings = string.split(" ");
        List<T> objects = new ArrayList<>();

        for (String s : strings)
            objects.add(deserialize(s));

        // The list must not be empty since this would break our brute force method.
        if (objects.isEmpty())
            return null;

        return bruteForceToArray(objects);
    }

    /***
     * This function is used to convert a generic list to a generic array.
     * The function is pretty nasty an insatiable but it seems like it is the only way.
     * @param list Generic list.
     * @return Generic array with the same type as the given list.
     */
    @SuppressWarnings("unchecked")
    private T[] bruteForceToArray(List<T> list) {
        T[] array = (T[]) Array.newInstance(list.get(0).getClass(), list.size());

        for (int i = 0; i < list.size(); i++)
            array[i] = list.get(i);
        return array;
    }
}
