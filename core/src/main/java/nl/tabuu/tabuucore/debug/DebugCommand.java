package nl.tabuu.tabuucore.debug;

import nl.tabuu.tabuucore.command.CCommand;
import nl.tabuu.tabuucore.command.CommandArgument;
import nl.tabuu.tabuucore.command.CommandArgumentType;
import nl.tabuu.tabuucore.command.CommandResult;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DebugCommand extends CCommand {

    public DebugCommand() {
        super("nl/tabuu/tabuucore");
        addArgument(CommandArgumentType.OFFLINE_PLAYER, true);
    }

    @Override
    public CommandResult onCommand(CommandSender sender, List<CommandArgument<?>> arguments) {
        OfflinePlayer player = arguments.get(0).getValue();
        sender.sendMessage(player.getUniqueId().toString());

        return CommandResult.SUCCESS;
    }
}
