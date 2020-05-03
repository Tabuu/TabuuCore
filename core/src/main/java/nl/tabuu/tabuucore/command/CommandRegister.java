package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.command.annotation.CommandExecutor;
import nl.tabuu.tabuucore.command.annotation.ChildCommand;
import nl.tabuu.tabuucore.command.argument.ArgumentConverter;
import nl.tabuu.tabuucore.command.argument.converter.OrderedArgumentConverter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Command utility class used for registering methods that use the {@link CommandExecutor} annotation.
 */
public class CommandRegister {

    /**
     * Registers a class containing methods annotated with {@link CommandExecutor}.
     * @param object The object containing methods annotated with {@link CommandExecutor}
     * @param plugin The plugin to register the commands to.
     */
    public void registerExecutors(Object object, JavaPlugin plugin) {
        if(Objects.isNull(object))
            throw new IllegalArgumentException("Object may not be null");

        Class<?> clazz = object.getClass();
        for(Method method : clazz.getDeclaredMethods()) {
            if(!isValidExecutor(method)) continue;

            Command root = getCommandFromCommandExecutor(object, method);
            if(root.hasParent()) continue;

            plugin.getCommand(root.getName()).setExecutor(root);
        }
    }

    private boolean isValidExecutor(Method method) {
        if(method.getParameterCount() != 2) return false;
        if(!CommandSender.class.isAssignableFrom(method.getParameterTypes()[0])) return false;
        if(!List.class.isAssignableFrom(method.getParameterTypes()[1])) return false;
        if(!method.getReturnType().equals(CommandResult.class)) return false;
        if(!method.isAnnotationPresent(CommandExecutor.class)) return false;

        return true;
    }

    private Command getCommandFromCommandExecutor(Object object, Method method) {
        if(!isValidExecutor(method))
            throw new IllegalArgumentException("Method is not a valid executor");
        CommandExecutor executor = method.getAnnotation(CommandExecutor.class);

        SenderType requiredSenderType = SenderType.ANY;

        if(method.getParameterTypes()[0].equals(Player.class))
            requiredSenderType = SenderType.PLAYER;

        else if (method.getParameterTypes()[0].equals(ConsoleCommandSender.class))
            requiredSenderType = SenderType.CONSOLE;

        final SenderType senderType = requiredSenderType;

        method.setAccessible(true);

        Command command = new Command(executor.command()) {
            @Override
            protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
                List<?> args;
                if(executor.requireFullSequence()) {
                    if(!arguments.stream().allMatch(Optional::isPresent)) return CommandResult.WRONG_SYNTAX;
                    args = arguments.stream().map(Optional::get).collect(Collectors.toList());
                } else args = arguments;

                try {
                    return (CommandResult) method.invoke(object, senderType.getClassType().cast(sender), args);
                } catch (InvocationTargetException e) {
                    e.getCause().printStackTrace();
                    return CommandResult.WRONG_SYNTAX;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return CommandResult.WRONG_SYNTAX;
                }
            }
        };

        ArgumentConverter converter = new OrderedArgumentConverter().setSequence(executor.argumentSequence()).setParameter(executor.parameter());
        command.setArgumentConverter(converter);
        command.setRequiredSenderType(requiredSenderType);

        if(executor.children().length > 0) {
            for(ChildCommand childMeta : executor.children()) {
                Method subMethod = null;
                for(Method possibleSubMethod : object.getClass().getDeclaredMethods()) {
                    if(possibleSubMethod.getName().equals(childMeta.method())){
                        subMethod = possibleSubMethod;
                        break;
                    }
                }

                if(subMethod == null) continue;

                Command subCommand = getCommandFromCommandExecutor(object, subMethod);
                command.addSubCommand(childMeta.label(), subCommand);
            }
        }

        return command;
    }
}
