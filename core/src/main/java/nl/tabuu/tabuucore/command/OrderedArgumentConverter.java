package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderedArgumentConverter extends ArgumentConverter {

    private List<ArgumentType> _argumentSequence;
    private ArgumentType _parameterType;

    public OrderedArgumentConverter(){
        _argumentSequence = new ArrayList<>();
    }

    public OrderedArgumentConverter setSequence(ArgumentType... sequence){
        _argumentSequence.addAll(Arrays.asList(sequence));
        return this;
    }

    public OrderedArgumentConverter addParameter(ArgumentType parameterType){
        _parameterType = parameterType;
        return this;
    }

    @Override
    public List<Optional<?>> convert(CommandSender feedBackReceiver, String[] arguments){
        String[] convertedArguments = Serializer.STRING.deserializeArray(String.join(" ", arguments));
        List<Optional<?>> converted = new ArrayList<>();

        for(int i = 0; i < convertedArguments.length; i++){
            if(i > _argumentSequence.size())
                break;

            ArgumentType type = _argumentSequence.get(i);
            Optional<?> value = convertArgument(convertedArguments[i], type);

            if(value.isPresent())
                converted.add(value);
            else{
                String errorMessage = "ERROR_PARSING_" + type.name();
                if(!_local.containsKey(errorMessage))
                    errorMessage = "ERROR_PARSING_UNKNOWN";
                feedBackReceiver.sendMessage(_local.translate(errorMessage, "{ARG}", convertedArguments[i]));
                return null;
            }
        }

        if(_parameterType != null && convertedArguments.length > _argumentSequence.size()){
            for(int i = _argumentSequence.size(); i < convertedArguments.length; i++){
                converted.add(convertArgument(convertedArguments[i], _parameterType));
            }
        }
        else if(convertedArguments.length < _argumentSequence.size()){
            for(int i = convertedArguments.length; i < _argumentSequence.size(); i++){
                converted.add(Optional.empty());
            }
        }

        return converted;
    }
}
