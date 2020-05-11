package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.command.annotation.CommandExecutor;
import nl.tabuu.tabuucore.command.annotation.ChildCommand;
import nl.tabuu.tabuucore.command.annotation.TabSuggester;
import nl.tabuu.tabuucore.command.argument.ArgumentConverter;
import nl.tabuu.tabuucore.command.argument.converter.OrderedArgumentConverter;
import org.bukkit.command.CommandSender;
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
        if(!method.isAnnotationPresent(CommandExecutor.class)) return false;
        if(method.getParameterCount() < 2) return false;

        if(!CommandSender.class.isAssignableFrom(method.getParameterTypes()[0])) return false;
        if(!List.class.isAssignableFrom(method.getParameterTypes()[1])) return false;

        if(!CommandResult.class.isAssignableFrom(method.getReturnType())) return false;

        return true;
    }

    private boolean isValidTabSuggester(Method method) {
        if(!method.isAnnotationPresent(TabSuggester.class)) return false;
        if(method.getParameterCount() < 4) return false;

        if(!CommandSender.class.isAssignableFrom(method.getParameterTypes()[0])) return false;
        if(!List.class.isAssignableFrom(method.getParameterTypes()[1])) return false;
        if(!String.class.isAssignableFrom(method.getParameterTypes()[2])) return false;
        if(!List.class.isAssignableFrom(method.getParameterTypes()[3])) return false;

        if(!List.class.isAssignableFrom(method.getReturnType())) return false;

        return true;
    }

    private Method getTabSuggestMethodFromCommandExecutor(Object object, CommandExecutor executor) {
        if(executor.tabSuggestMethod().isEmpty()) return null;

        for(Method method : object.getClass().getDeclaredMethods()) {
            boolean sameName = method.getName().equals(executor.tabSuggestMethod());
            if(sameName && isValidTabSuggester(method))
                return method;
        }

        return null;
    }

    private Command getCommandFromCommandExecutor(Object object, Method method) {
        if(!isValidExecutor(method))
            throw new IllegalArgumentException("Method is not a valid executor");

        CommandExecutor executor = method.getAnnotation(CommandExecutor.class);

        SenderType senderType = SenderType.ANY;

        for(SenderType type : SenderType.values()) {
            if(method.getParameterTypes()[0].equals(type.getClassType()))
                senderType = type;
        }

        final SenderType finalSenderType = senderType;

        Method tabSuggester = getTabSuggestMethodFromCommandExecutor(object, executor);
        if(tabSuggester != null) tabSuggester.setAccessible(true);
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
                    return (CommandResult) method.invoke(object, finalSenderType.getClassType().cast(sender), args);
                } catch (InvocationTargetException e) {
                    e.getCause().printStackTrace();
                    return CommandResult.WRONG_SYNTAX;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return CommandResult.WRONG_SYNTAX;
                }
            }

            @Override
            protected List<String> onTabSuggest(CommandSender sender, List<String> arguments, String partial, List<String> suggestions) {
                if(tabSuggester != null) {
                    try {
                        List<?> value = (List<?>) tabSuggester.invoke(object, finalSenderType.getClassType().cast(sender), arguments, partial, suggestions);
                        return (List<String>) value;

                    } catch (InvocationTargetException e) {
                        e.getCause().printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return super.onTabSuggest(sender, arguments, partial, suggestions);
            }
        };

        ArgumentConverter converter = new OrderedArgumentConverter().setSequence(executor.argumentSequence()).setParameter(executor.parameter());
        command.setArgumentConverter(converter);
        command.setRequiredSenderType(senderType);

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
