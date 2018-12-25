package nl.tabuu.tabuucore.debug;

import nl.tabuu.tabuucore.command.CCommand;
import nl.tabuu.tabuucore.command.CommandArgument;
import nl.tabuu.tabuucore.command.CommandArgumentType;
import nl.tabuu.tabuucore.command.CommandResult;
import nl.tabuu.tabuucore.nms.wrapper.ITicksPerSecond;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DebugCommand extends CCommand {

    public DebugCommand() {
        super("tabuucore");
    }

    @Override
    public CommandResult onCommand(CommandSender sender, List<CommandArgument<?>> arguments) {
        sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "TPS: " + ITicksPerSecond.get().getTPS());

        return CommandResult.SUCCESS;
    }
}
