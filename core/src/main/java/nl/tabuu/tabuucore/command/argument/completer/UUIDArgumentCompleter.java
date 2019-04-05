package nl.tabuu.tabuucore.command.argument.completer;

import nl.tabuu.tabuucore.command.argument.IArgumentCompleter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UUIDArgumentCompleter implements IArgumentCompleter {
    @Override
    public List<String> complete(CommandSender sender, String partialArgument) {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getUniqueId)
                .map(UUID::toString)
                .collect(Collectors.toList());
    }
}
