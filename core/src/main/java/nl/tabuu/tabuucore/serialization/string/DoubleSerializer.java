package nl.tabuu.tabuucore.serialization.string;

public class DoubleSerializer extends AbstractStringSerializer<Double> {
    @Override
    public String serialize(Double number) {
        if(Math.round(number) == number)
            return String.format("%.0f", number);

        return number.toString();
    }

    @Override
    public Double deserialize(String string) {
        try {
            return Double.parseDouble(string);
        }
        catch (NumberFormatException exception){
            return null;
        }
    }
}
