package nl.tabuu.tabuucore.command.annotation;

import nl.tabuu.tabuucore.command.Command;
import nl.tabuu.tabuucore.command.argument.ArgumentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define command executors.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandExecutor {
    /**
     * Returns the name of the command, as defined in the plugin.yml.
     * @return The name of the command, as defined in the plugin.yml.
     * @see Command#getName()
     */
    String command();

    /**
     * Returns an ordered array containing the sequence of the argument types.
     * @return An ordered array containing the sequence of the argument types.
     * @see nl.tabuu.tabuucore.command.argument.converter.OrderedArgumentConverter#setSequence(ArgumentType... types)
     */
    ArgumentType[] argumentSequence() default {};

    /**
     * Returns the parameter type.
     * @return The parameter type.
     * @see nl.tabuu.tabuucore.command.argument.converter.OrderedArgumentConverter#setParameter(ArgumentType type)
     */
    ArgumentType parameter() default ArgumentType.NULL;

    /**
     * Returns whether or not the full sequence of {@link #argumentSequence()} should be present.
     * When true, a list of optional will be returned, else a raw list.
     * @return Whether or not the full sequence of {@link #argumentSequence()} should be present.
     */
    boolean requireFullSequence() default false;

    /**
     * Returns an array of child commands.
     * @return An array of child commands.
     */
    ChildCommand[] children() default {};
}
