package nl.tabuu.tabuucore.command.argument;

import nl.tabuu.tabuucore.command.argument.completer.*;
import nl.tabuu.tabuucore.serialization.string.AbstractStringSerializer;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * Enum representing the returned object-type of an argument.
 */
public enum ArgumentType {

    /**
     * Represents a {@link org.bukkit.entity.Player} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.PlayerSerializer} class.
     */
    PLAYER(Serializer.PLAYER, new PlayerArgumentCompleter()),

    /**
     * Represents a {@link org.bukkit.OfflinePlayer} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.OfflinePlayerSerializer} class.
     */
    OFFLINE_PLAYER(Serializer.OFFLINE_PLAYER, new OfflinePlayerArgumentCompleter()),

    /**
     * Represents an {@link Integer} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.IntegerSerializer} class.
     */
    INTEGER(Serializer.INTEGER, null),

    /**
     * Represents a {@link Long} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LongSerializer} class.
     */
    LONG(Serializer.LONG, null),

    /**
     * Represents a {@link Double} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.DoubleSerializer} class.
     */
    DOUBLE(Serializer.DOUBLE, null),

    /**
     * Represents a {@link String} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.StringSerializer} class.
     */
    STRING(Serializer.STRING, null),

    /**
     * Represents a {@link Character} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.CharacterSerializer} class.
     */
    CHARACTER(Serializer.CHARACTER, null),

    /**
     * Represents a {@link Boolean} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.BooleanSerializer} class.
     */
    BOOLEAN(Serializer.BOOLEAN, new BooleanArgumentCompleter()),

    /**
     * Represents a {@link org.bukkit.Location} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.LocationSerializer} class.
     */
    LOCATION(Serializer.LOCATION, new LocationArgumentCompleter()),

    /**
     * Represents a {@link java.util.UUID} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.UUIDSerializer} class.
     */
    UUID(Serializer.UUID, new UUIDArgumentCompleter()),

    /**
     * Represents a {@link Long} object-type deserialized by the {@link nl.tabuu.tabuucore.serialization.string.TimeSerializer} class.
     */
    TIME(Serializer.TIME, new TimeArgumentCompleter()),

    /**
     * Represents a null-type. For internal use only!
     */
    NULL(null, null);

    private AbstractStringSerializer<?> _serializer;
    private IArgumentCompleter _completer;

    ArgumentType(AbstractStringSerializer serializer, IArgumentCompleter completer){
        _serializer = serializer;
        _completer = completer;
    }

    /**
     * Returns a list of possible auto-completions based on the specified partial argument, and the ArgumentType's {@link IArgumentCompleter}.
     * @param sender The sender auto-completing the partial argument.
     * @param partialArgument The partial argument.
     * @return A list of possible auto-completions based on the specified partial argument, and the ArgumentType's {@link IArgumentCompleter}.
     */
    public List<String> complete(CommandSender sender, String partialArgument){
        if(_completer == null)
            return Collections.emptyList();
        return _completer.complete(sender, partialArgument);
    }

    /**
     * Deserializes the specified string using the ArgumentType's deserializer.
     * @param argument The string to be deserialized.
     * @param <T> The object-type corresponding with the ArgumentType's deserializer's return type.
     * @return The deserialized argument.
     */
    public <T> T convert(String argument){
        if(_serializer != null)
            return (T) _serializer.deserialize(argument);
        return null;
    }
}
