package nl.tabuu.tabuucore.serialization.string;

public class CharacterSerializer extends AbstractStringSerializer<Character> {
    @Override
    public String serialize(Character character) {
        return character.toString();
    }

    @Override
    public Character deserialize(String string) {
        if(string == null || string.length() < 1)
            return null;

        return string.charAt(0);
    }
}
