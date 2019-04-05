package nl.tabuu.tabuucore.command.argument.completer;

import nl.tabuu.tabuucore.command.argument.IArgumentCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerArgumentCompleter implements IArgumentCompleter {

    @Override
    public List<String> complete(CommandSender sender, String partialArgument) {
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

}
