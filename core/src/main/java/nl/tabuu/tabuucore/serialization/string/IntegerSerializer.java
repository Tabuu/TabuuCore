package nl.tabuu.tabuucore.serialization.string;

import java.util.Objects;

public class IntegerSerializer extends AbstractStringSerializer<Integer> {
    @Override
    public String serialize(Integer number) {
        if(Objects.isNull(number)) return null;

        return number.toString();
    }

    @Override
    public Integer deserialize(String string) {
        if(Objects.isNull(string)) return null;

        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException exception){
            return null;
        }
    }
}
