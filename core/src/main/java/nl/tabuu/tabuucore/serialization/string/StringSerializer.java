package nl.tabuu.tabuucore.serialization.string;

import java.util.ArrayList;
import java.util.List;

public class StringSerializer extends AbstractStringSerializer<String> {
    @Override
    public String serialize(String object) {
        return null;
    }

    @Override
    public String deserialize(String value) {
        return null;
    }

    @Override
    public String serializeArray(String[] array){
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < array.length; i++){
            StringBuilder b = new StringBuilder();
            String string = array[i];

            if(string.contains(" "))
                b.append("\"");

            for(int j = 0; j < string.length(); j++) {
                char character = string.charAt(j);
                if(needsEscapeChar(character))
                    b.append('\\');
                b.append(character);
            }

            builder.append(b.toString());
            if(i < array.length - 1)
                builder.append(' ');
        }

        return builder.toString();
    }

    @Override
    public String[] deserializeArray(String string){
        List<String> stringList = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < string.length(); i++){
            char character = string.charAt(i);
            if(character == '\\'){
                if(i < string.length() - 1){
                    builder.append(string.charAt(i + 1));
                    i++;
                }
            }
            else if(character == '"'){
                stringList.add(builder.toString());
                builder = new StringBuilder();
            }
            else
                builder.append(character);
        }

        return stringList.stream().toArray(String[]::new);
    }

    private boolean needsEscapeChar(char character){

        switch (character){
            case '\\':
            case '"':
                return true;

            default:
                return false;
        }

    }
}
