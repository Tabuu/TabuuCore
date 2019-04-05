package nl.tabuu.tabuucore.command.argument;

import nl.tabuu.tabuucore.command.argument.completer.*;
import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public enum ArgumentType {

    PLAYER(Serializer.PLAYER, new PlayerArgumentCompleter()),

    OFFLINE_PLAYER(Serializer.OFFLINE_PLAYER, new OfflinePlayerArgumentCompleter()),

    INTEGER(Serializer.INTEGER, null),

    LONG(Serializer.LONG, null),

    DOUBLE(Serializer.DOUBLE, null),

    STRING(Serializer.STRING, null),

    CHARACTER(Serializer.CHARACTER, null),

    LOCATION(Serializer.LOCATION, new LocationArgumentCompleter()),

    UUID(Serializer.UUID, new UUIDArgumentCompleter()),

    TIME(Serializer.TIME, new TimeArgumentCompleter()),

    NULL(null, null);

    private AbstractStringSerializer<?> _serializer;
    private IArgumentCompleter _completer;

    ArgumentType(AbstractStringSerializer serializer, IArgumentCompleter completer){
        _serializer = serializer;
        _completer = completer;
    }

    public List<String> complete(CommandSender sender, String partialArgument){
        if(_completer == null)
            return Collections.emptyList();
        return _completer.complete(sender, partialArgument);
    }

    public <T> T convert(String argument){
        if(_serializer != null)
            return (T) _serializer.deserialize(argument);
        return null;
    }
}
