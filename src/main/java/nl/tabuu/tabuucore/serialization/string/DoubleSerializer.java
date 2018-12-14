package nl.tabuu.tabuucore.serialization.string;

public class DoubleSerializer extends AbstractStringSerializer<Double> {
    @Override
    public String serialize(Double number) {
        return number.toString();
    }

    @Override
    public Double deserialize(String string) {
        return Double.parseDouble(string);
    }
}
