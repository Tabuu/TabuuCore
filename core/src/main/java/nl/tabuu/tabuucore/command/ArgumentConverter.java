package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public abstract class ArgumentConverter {

    Dictionary _local;

    protected ArgumentConverter(){
        _local = TabuuCore.getInstance().getConfigurationManager().getConfiguration("lang").getDictionary("");
    }

    abstract List<Optional<?>> convert(CommandSender feedbackReceiver, String[] arguments);

    protected Optional<?> convertArgument(String argument, ArgumentType type){
        Object value = type.convert(argument);

        if(value == null)
            return Optional.empty();

        return Optional.of(value);
    }
}
