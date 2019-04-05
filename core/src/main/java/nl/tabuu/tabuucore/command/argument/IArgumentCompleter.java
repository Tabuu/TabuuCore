package nl.tabuu.tabuucore.command.argument;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IArgumentCompleter {

    List<String> complete(CommandSender sender, String partialArgument);

}
