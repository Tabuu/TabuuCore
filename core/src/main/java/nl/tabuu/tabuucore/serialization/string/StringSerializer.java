package nl.tabuu.tabuucore.serialization.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringSerializer extends AbstractStringSerializer<String> {
    @Override
    public String serialize(String object) {
        if (Objects.isNull(object)) return null;

        return object;
    }

    @Override
    public String deserialize(String value) {
        return value;
    }

    @Override
    public String serializeArray(String[] array) {
        if(Objects.isNull(array)) return null;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            StringBuilder b = new StringBuilder();
            String string = array[i];

            if (string.contains(" "))
                b.append("\"");

            for (int j = 0; j < string.length(); j++) {
                char character = string.charAt(j);
                if (needsEscapeChar(character))
                    b.append('\\');
                b.append(character);
            }

            builder.append(b.toString());
            if (i < array.length - 1)
                builder.append(' ');
        }

        return builder.toString();
    }

    @Override
    public String[] deserializeArray(String string) {
        if(Objects.isNull(string)) return null;

        List<String> stringList = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        boolean openQuote = false;
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if (character == '\\') {
                if (i < string.length() - 1) {
                    builder.append(string.charAt(i + 1));
                    i++;
                }
            } else if (character == ' ' && !openQuote) {
                stringList.add(builder.toString());
                builder = new StringBuilder();
            } else if (character == '"')
                openQuote ^= true;
            else
                builder.append(character);
        }
        if (builder.length() > 0)
            stringList.add(builder.toString());

        return stringList.stream().toArray(String[]::new);
    }

    private boolean needsEscapeChar(char character) {

        switch (character) {
            case '\\':
            case '"':
                return true;

            default:
                return false;
        }

    }
}
