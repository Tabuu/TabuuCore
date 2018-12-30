package nl.tabuu.tabuucore.debug;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.command.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class ExampleCommand extends Command {

    public ExampleCommand() {
        super("example");
        addSubCommand("sub", new ExampleSubCommand(this));
        setArgumentConverter(new OrderedArgumentConverter().setSequence(ArgumentType.PLAYER, ArgumentType.INTEGER).addParameter(ArgumentType.STRING));
    }

    @Override
    protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
        for(Optional optional : arguments) {
            if (!optional.isPresent())
                return CommandResult.WRONG_SYNTAX;
        }

        Player player = (Player) arguments.get(0).get();
        int number = (Integer) arguments.get(1).get();

        for(Optional<?> optional : arguments.subList(2, arguments.size())){
            sender.sendMessage(number * 2 + optional.get().toString());
        }

        return CommandResult.SUCCESS;
    }

    class ExampleSubCommand extends Command{

        protected ExampleSubCommand(Command parent) {
            super("example sub", parent);

            setArgumentConverter(new OrderedArgumentConverter().setSequence(ArgumentType.LOCATION));
            setRequiredSenderType(SenderType.PLAYER);
        }

        @Override
        protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
            if(arguments.get(0).isPresent())
                ((Player) sender).teleport((Location) arguments.get(0).get());

            return CommandResult.SUCCESS;
        }
    }
}
