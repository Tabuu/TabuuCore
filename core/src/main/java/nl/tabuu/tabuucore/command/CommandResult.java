package nl.tabuu.tabuucore.command;

/**
 * Enum representing the result of an executed command.
 */
public enum CommandResult {
    /**
     * Represents a successfully executed command.
     */
    SUCCESS,

    /**
     * Represents a command executing without (sufficient) permission.
     */
    NO_PERMISSION,

    /**
     * Represents a command executed with incorrect syntax.
     */
    WRONG_SYNTAX
}
