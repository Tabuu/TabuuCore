package nl.tabuu.tabuucore.command.argument.converter;

import nl.tabuu.tabuucore.command.argument.ArgumentConverter;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public class OrderedVariableArgumentConverter extends ArgumentConverter {

    @Override
    public List<String> completeArgument(CommandSender sender, String... arguments) {
        return null;
    }

    @Override
    public List<Optional<?>> convertArguments(CommandSender sender, String... arguments) {
        return null;
    }



}
