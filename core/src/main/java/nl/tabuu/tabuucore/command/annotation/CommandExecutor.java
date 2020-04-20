package nl.tabuu.tabuucore.command.annotation;

import nl.tabuu.tabuucore.command.argument.ArgumentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandExecutor {
    String command();
    ArgumentType[] argumentSequence() default {};
    ArgumentType parameter() default ArgumentType.NULL;
    boolean requireFullSequence() default false;
    ChildCommand[] children() default {};
}
