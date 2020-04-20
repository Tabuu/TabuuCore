package nl.tabuu.tabuucore.command.argument.converter;

import nl.tabuu.tabuucore.command.argument.ArgumentConverter;
import nl.tabuu.tabuucore.command.argument.ArgumentType;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * An {@link ArgumentConverter} that converts arguments in the order of the provided String array.
 */
// TODO: Recode for more readability.
public class OrderedArgumentConverter extends ArgumentConverter {

    private List<ArgumentType> _argumentSequence;
    private ArgumentType _parameterType;

    public OrderedArgumentConverter(){
        _argumentSequence = new ArrayList<>();
        _parameterType = ArgumentType.NULL;
    }

    @Override
    public List<String> completeArgument(CommandSender sender, String... arguments) {
        String[] convertedArguments = Serializer.STRING.deserializeArray(String.join(" ", arguments));

        if(arguments.length <= 0 || (_parameterType.equals(ArgumentType.NULL) && _argumentSequence.size() < arguments.length))
            return new ArrayList<>();

        String partialArgument = arguments[arguments.length - 1];
        ArgumentType type = _argumentSequence.size() < arguments.length ? _parameterType : _argumentSequence.get(arguments.length - 1);

        return type.complete(sender, partialArgument);
    }

    public OrderedArgumentConverter setSequence(ArgumentType... sequence){
        _argumentSequence.addAll(Arrays.asList(sequence));
        return this;
    }

    /**
     * @deprecated Name changed to setParameter, as it doesn't 'add' anything.
     */
    @Deprecated
    public OrderedArgumentConverter addParameter(ArgumentType parameterType){
        return setParameter(parameterType);
    }

    public OrderedArgumentConverter setParameter(ArgumentType parameterType){
        _parameterType = parameterType;
        return this;
    }

    /**
     * Converts the provided arguments based on the {@link ArgumentType}s.
     * If an argument from the {@link #setSequence(ArgumentType...) is missing the optional will be empty.}
     * @param feedBackReceiver The sender of the arguments.
     * @param arguments The arguments to be converted/deserialized.
     * @return A list of all converted arguments, wrapped in an {@link Optional}, if an argument is missing a {@link Optional#empty()} is given.
     */
    @Override
    public List<Optional<?>> convertArguments(CommandSender feedBackReceiver, String... arguments){
        String[] convertedArguments = Serializer.STRING.deserializeArray(String.join(" ", arguments));
        List<Optional<?>> converted = new ArrayList<>();

        for(int i = 0; i < convertedArguments.length; i++){
            if(i >= _argumentSequence.size())
                break;

            ArgumentType type = _argumentSequence.get(i);
            Optional<?> value = convertArgument(convertedArguments[i], type);

            converted.add(value);

            if(!value.isPresent()) {
                String errorMessage = "ERROR_PARSING_" + type.name();
                if(!_local.containsKey(errorMessage))
                    errorMessage = "ERROR_PARSING_UNKNOWN";
                feedBackReceiver.sendMessage(_local.translate(errorMessage, "{ARG}", convertedArguments[i]));
            }
        }

        if(!_parameterType.equals(ArgumentType.NULL) && convertedArguments.length > _argumentSequence.size()){
            for(int i = _argumentSequence.size(); i < convertedArguments.length; i++)
                converted.add(convertArgument(convertedArguments[i], _parameterType));
        }
        else if(convertedArguments.length < _argumentSequence.size()){
            for(int i = convertedArguments.length; i < _argumentSequence.size(); i++)
                converted.add(Optional.empty());
        }

        return converted;
    }

}
