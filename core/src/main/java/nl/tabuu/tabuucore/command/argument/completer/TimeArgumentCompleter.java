package nl.tabuu.tabuucore.command.argument.completer;

import nl.tabuu.tabuucore.command.argument.IArgumentCompleter;
import nl.tabuu.tabuucore.serialization.string.Serializer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class TimeArgumentCompleter implements IArgumentCompleter {
    @Override
    public List<String> complete(CommandSender sender, String partialArgument) {
        String zero = "\"" + Serializer.TIME.serialize(0L) + "\"";
        String now = "\"" + Serializer.TIME.serialize(System.currentTimeMillis()) + "\"";

        return Arrays.asList(zero, now, "\"0y 0d 0h 0m 0s 0ms\"");
    }
}
