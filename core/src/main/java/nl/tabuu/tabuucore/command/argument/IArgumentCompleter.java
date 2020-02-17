package nl.tabuu.tabuucore.command.argument;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface IArgumentCompleter {

    /**
     * Returns a list of possible auto-completions based on the specified partial argument.
     * @param sender The sender auto-completing the partial argument.
     * @param partialArgument The partial argument.
     * @return A list of possible auto-completions based on the specified partial argument.
     */
    List<String> complete(CommandSender sender, String partialArgument);

}
