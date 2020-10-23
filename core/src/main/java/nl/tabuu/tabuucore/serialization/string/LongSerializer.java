package nl.tabuu.tabuucore.serialization.string;

import java.util.Objects;

public class LongSerializer extends AbstractStringSerializer<Long> {
    @Override
    public String serialize(Long number) {
        if(Objects.isNull(number)) return null;

        return number.toString();
    }

    @Override
    public Long deserialize(String string) {
        if(Objects.isNull(string)) return null;

        try {
            return Long.parseLong(string);
        }
        catch (NumberFormatException exception){
            return null;
        }
    }
}
