package nl.tabuu.tabuucore.command.argument;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public abstract class ArgumentConverter {

    protected Dictionary _local;

    protected ArgumentConverter(){
        _local = TabuuCore.getInstance().getLocal();
    }

    /**
     * Returns a list of possible auto-completions of the last argument in the array.
     * @param sender The {@link CommandSender} auto-completing the partial argument.
     * @param arguments An array containing all arguments, including the partial argument.
     * @return A list of possible auto-completions.
     */
    public abstract List<String> completeArgument(CommandSender sender, String... arguments);

    /**
     * Returns a list of {@link Optional}. The {@link Optional} contains a converted argument, or empty if the argument could not be deserialized or was not specified.
     * @param sender The {@link CommandSender} executing the command.
     * @param arguments The arguments to be converted/deserialized.
     * @return a list of {@link Optional}. The {@link Optional} contains a converted argument, or empty if the argument could not be deserialized or was not specified.
     */
    public abstract List<Optional<?>> convertArguments(CommandSender sender, String... arguments);

    /**
     * Converts a single argument to its deserialized object.
     * @param argument The argument to be converted.
     * @param type The argument's type.
     * @return The deserialized object wrapped in an {@link Optional}.
     */
    protected Optional<?> convertArgument(String argument, ArgumentType type){
        Object value = type.convert(argument);

        if(value == null)
            return Optional.empty();

        return Optional.of(value);
    }
}
