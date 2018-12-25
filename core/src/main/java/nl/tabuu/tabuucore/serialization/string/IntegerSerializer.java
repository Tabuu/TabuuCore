package nl.tabuu.tabuucore.serialization.string;

public class IntegerSerializer extends AbstractStringSerializer<Integer> {
    @Override
    public String serialize(Integer number) {
        return number.toString();
    }

    @Override
    public Integer deserialize(String string) {
        return Integer.parseInt(string);
    }
}