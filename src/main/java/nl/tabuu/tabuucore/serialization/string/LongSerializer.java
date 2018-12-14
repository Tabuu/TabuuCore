package nl.tabuu.tabuucore.serialization.string;

public class LongSerializer extends AbstractStringSerializer<Long> {
    @Override
    public String serialize(Long number) {
        return number.toString();
    }

    @Override
    public Long deserialize(String string) {
        return Long.parseLong(string);
    }
}
