package nl.tabuu.tabuucore.serialization.string;

import java.util.Objects;

public class DoubleSerializer extends AbstractStringSerializer<Double> {
    @Override
    public String serialize(Double number) {
        if(Objects.isNull(number)) return null;

        if(Math.round(number) == number)
            return String.format("%.0f", number);

        return number.toString().replace('.', '_');
    }

    @Override
    public Double deserialize(String string) {
        if(Objects.isNull(string)) return null;

        try {
            return Double.parseDouble(string.replace('_', '.'));
        }
        catch (NumberFormatException exception){
            return null;
        }
    }
}
