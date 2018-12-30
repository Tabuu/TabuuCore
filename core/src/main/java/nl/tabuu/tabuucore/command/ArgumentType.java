package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ArgumentType {

    PLAYER(Serializer.PLAYER),

    OFFLINE_PLAYER(Serializer.OFFLINE_PLAYER),

    INTEGER(Serializer.INTEGER),

    LONG(Serializer.LONG),

    DOUBLE(Serializer.DOUBLE),

    STRING(Serializer.STRING),

    CHARACTER(Serializer.CHARACTER),

    LOCATION(Serializer.LOCATION),

    UUID(Serializer.UUID),

    NULL(null);

    private AbstractStringSerializer<?> _serializer;

    ArgumentType(AbstractStringSerializer serializer){
        _serializer = serializer;
    }

    public <T> T convert(String argument){
        if(_serializer != null)
            return (T) _serializer.deserialize(argument);
        return null;
    }
}
