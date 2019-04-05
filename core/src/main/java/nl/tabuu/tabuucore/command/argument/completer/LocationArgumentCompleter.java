package nl.tabuu.tabuucore.command.argument.completer;

import nl.tabuu.tabuucore.command.argument.IArgumentCompleter;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.List;

public class LocationArgumentCompleter implements IArgumentCompleter {
    @Override
    public List<String> complete(CommandSender sender, String partialArgument) {


        String completedArgument = "\"world 0 0 0\"";

        if(sender instanceof Entity){
            Entity entity = (Entity) sender;
            Location location = entity.getLocation();

            completedArgument = "\"" +
                    location.getWorld().getName() + " " +
                    location.getBlockX() + " " +
                    location.getBlockY() + " " +
                    location.getBlockZ() + " " +
                    location.getPitch() + " " +
                    location.getYaw() + "\"";
        }

        return Arrays.asList(completedArgument);
    }
}
