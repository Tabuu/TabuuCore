package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.command.argument.ArgumentConverter;
import nl.tabuu.tabuucore.command.argument.converter.OrderedArgumentConverter;
import nl.tabuu.tabuucore.command.register.annotation.ChildCommand;
import nl.tabuu.tabuucore.command.register.annotation.CommandExecutor;
import nl.tabuu.tabuucore.command.register.annotation.TabSuggester;
import nl.tabuu.tabuucore.command.register.exception.CommandRegisterException;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
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

        for(Method method : findMethods(object, this::isValidExecutor)) {
            Command root = getCommandFromCommandExecutor(object, method);
            if(root.hasParent()) continue;

            plugin.getCommand(root.getName()).setExecutor(root);
        }
    }

    private List<Method> findMethods(Object object, Predicate<Method> filter) {
        return Arrays.stream(object.getClass().getDeclaredMethods())
                .filter(filter)
                .collect(Collectors.toList());
    }

    private Method findMethod(Object object, String name, Predicate<Method> validator) {
        List<Method> matches = findMethods(object, (method) -> method.getName().equals(name) && validator.test(method));

        if(matches.isEmpty())
            throw new CommandRegisterException(String.format("No valid method found with name %s.", name));

        if(matches.size() > 1)
            throw new CommandRegisterException(String.format("Multiple valid methods found with name %s.", name));

        return matches.stream().findAny().get();
    }

    private Class<?> getParameterizedTypeClass(Type type) {
        if(!(type instanceof ParameterizedType))
            throw new IllegalArgumentException("The provided type is not parameterizable.");

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] parameters = parameterizedType.getActualTypeArguments();

        if(parameters.length != 1)
            throw new IllegalArgumentException("The provided parameterizable type has more or less than 1 parameter.");

        Type parameter = parameters[0];

        if(parameter instanceof ParameterizedType)
            parameter = ((ParameterizedType) parameter).getRawType();

        if (parameter instanceof Class)
            return (Class<?>) parameter;

        else
            throw new IllegalArgumentException("Could not find class.");
    }

    private boolean isValidExecutor(Method method) {
        if(!method.isAnnotationPresent(CommandExecutor.class)) return false;
        if(method.getParameterCount() < 2) return false;

        Parameter sender = method.getParameters()[0];
        if(!CommandSender.class.isAssignableFrom(sender.getType())) return false;

        Parameter arguments = method.getParameters()[1];
        if(!List.class.isAssignableFrom(arguments.getType())) return false;

        if(!CommandResult.class.isAssignableFrom(method.getReturnType())) return false;

        return true;
    }

    private boolean isValidTabSuggester(Method method) {
        if(!method.isAnnotationPresent(TabSuggester.class)) return false;
        if(method.getParameterCount() < 4) return false;

        Parameter sender = method.getParameters()[0];
        if(!CommandSender.class.isAssignableFrom(sender.getType())) return false;

        Parameter arguments = method.getParameters()[1];
        if(!List.class.isAssignableFrom(arguments.getType())) return false;
        if(!String.class.isAssignableFrom(getParameterizedTypeClass(arguments.getParameterizedType()))) return false;

        Parameter partial = method.getParameters()[2];
        if(!String.class.isAssignableFrom(partial.getType())) return false;

        Parameter suggestions = method.getParameters()[3];
        if(!List.class.isAssignableFrom(suggestions.getType())) return false;
        if(!String.class.isAssignableFrom(getParameterizedTypeClass(suggestions.getParameterizedType()))) return false;

        if(!List.class.isAssignableFrom(method.getReturnType())) return false;

        return true;
    }

    private Method getTabSuggest(Object object, String methodName) {
        return findMethod(object, methodName, this::isValidTabSuggester);
    }

    private Command getCommandFromCommandExecutor(Object object, Method executorMethod) {
        if(!isValidExecutor(executorMethod))
            throw new CommandRegisterException(String.format("Method '%s' is not a valid executor.", executorMethod.getName()));

        executorMethod.setAccessible(true);
        CommandExecutor executor = executorMethod.getAnnotation(CommandExecutor.class);

        final Class<CommandSender> senderClass = (Class<CommandSender>) executorMethod.getParameterTypes()[0];
        final SenderType senderType = Arrays.stream(SenderType.values())
                .filter((type) -> type.getClassType().equals(senderClass))
                .findFirst()
                .orElse(SenderType.ANY);

        final boolean hasTabSuggester = !executor.tabSuggestMethod().isEmpty();
        final Method tabSuggesterMethod;
        if(hasTabSuggester) {
            tabSuggesterMethod = getTabSuggest(object, executor.tabSuggestMethod());
            tabSuggesterMethod.setAccessible(true);
        } else tabSuggesterMethod = null;

        Parameter argumentParameter = executorMethod.getParameters()[1];
        final boolean isFullSequenceRequired = !Optional.class.isAssignableFrom(getParameterizedTypeClass(argumentParameter.getParameterizedType()));

        Command command = new Command(executor.command()) {
            @Override
            protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
                List<?> convertedArguments;

                if(isFullSequenceRequired) {
                    if(!arguments.stream().allMatch(Optional::isPresent)) return CommandResult.WRONG_SYNTAX;
                    convertedArguments = arguments.stream().map(Optional::get).collect(Collectors.toList());
                } else convertedArguments = arguments;

                try {
                    return (CommandResult) executorMethod.invoke(object, senderType.getClassType().cast(sender), convertedArguments);
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
                if(hasTabSuggester) {
                    try {
                        List<?> value = (List<?>) tabSuggesterMethod.invoke(object, senderClass.cast(sender), arguments, partial, suggestions);
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

        ArgumentConverter converter = new OrderedArgumentConverter()
                .setSequence(executor.argumentSequence())
                .setParameter(executor.parameter());

        command.setArgumentConverter(converter);
        command.setRequiredSenderType(senderType);

        if(executor.children().length > 0) {
            for(ChildCommand childMeta : executor.children()) {
                Method subCommandExecutor = findMethod(object, childMeta.method(), this::isValidExecutor);
                Command subCommand = getCommandFromCommandExecutor(object, subCommandExecutor);
                command.addSubCommand(childMeta.label(), subCommand);
            }
        }

        return command;
    }
}
