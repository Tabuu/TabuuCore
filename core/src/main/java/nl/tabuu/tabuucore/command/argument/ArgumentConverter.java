package nl.tabuu.tabuucore.command.argument;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public abstract class ArgumentConverter {

    protected Dictionary _local;

    protected ArgumentConverter(){
        _local = TabuuCore.getInstance().getConfigurationManager().getConfiguration("lang").getDictionary("");
    }

    public abstract List<String> completeArgument(CommandSender sender, String[] arguments);

    @Deprecated
    public abstract List<Optional<?>> convert(CommandSender sender, String[] arguments);

    public List<Optional<?>> convertArguments(CommandSender sender, String[] arguments){
        return convert(sender, arguments);
    }

    protected Optional<?> convertArgument(String argument, ArgumentType type){
        Object value = type.convert(argument);

        if(value == null)
            return Optional.empty();

        return Optional.of(value);
    }
}
