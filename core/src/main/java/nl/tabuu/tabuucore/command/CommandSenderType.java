package nl.tabuu.tabuucore.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public enum CommandSenderType{
    PLAYER(Player.class),
    CONSOLE(ConsoleCommandSender.class);

    private Class _classType;

    <T extends CommandSender> CommandSenderType(Class<T> clazz){
        _classType = clazz;
    }

    public <T extends CommandSender> Class<T> getClassType(){
        return _classType;
    }
}
