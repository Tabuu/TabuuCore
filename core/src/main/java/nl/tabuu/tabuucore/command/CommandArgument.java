package nl.tabuu.tabuucore.command;

import java.util.List;

public class CommandArgument<T extends Object>{

    CommandArgumentType _type;
    String _argument;

    public CommandArgument(String argument, CommandArgumentType type) throws ClassCastException, IllegalArgumentException{
        _argument = argument;
        _type = type;
    }

    public <T> T getValue(){
        return (T) _type.convert(_argument);
    }

    public <T> List<T> getValues(){
        return (List<T>) _type.convertToList(_argument);
    }

    public CommandArgumentType getType() {
        return _type;
    }
}
