package nl.tabuu.tabuucore.command.register.annotation;

/**
 * Annotation used to define child commands of a {@link CommandExecutor}, by linking a method that is also annotated with {@link CommandExecutor}.
 */
public @interface ChildCommand {
    /**
     * Returns the label of this command in the parents context. This would be the same label used as label in the addSubcommand method of the {@link nl.tabuu.tabuucore.command.Command} class.
     * @return The label of this command in the parents context. This would be the same label used as label in the addSubcommand method of the {@link nl.tabuu.tabuucore.command.Command} class.
     */
    String label();

    /**
     * Returns the name of the child command's {@link CommandExecutor} method.
     * @return The name of the child command's {@link CommandExecutor} method.
     */
    String method();
}
