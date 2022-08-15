package nl.tabuu.tabuucore.command.argument.completer;

import nl.tabuu.tabuucore.command.argument.IArgumentCompleter;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class BooleanArgumentCompleter implements IArgumentCompleter {
    @Override
    public List<String> complete(CommandSender sender, String partialArgument) {
        return Arrays.asList("true", "false");
    }
}
