package nl.tabuu.tabuucore.serialization.string;

public class BooleanSerializer extends AbstractStringSerializer<Boolean> {

    @Override
    public String serialize(Boolean object) {
        return object ? "true" : "false";
    }

    @Override
    public Boolean deserialize(String value) {
        switch (value.toLowerCase()){
            case "true":
            case "1":
            case "on":
            case "t":
            case "yes":
                return true;

            case "false":
            case "0":
            case "off":
            case "f":
            case "no":
                return false;
        }

        return null;
    }

}
