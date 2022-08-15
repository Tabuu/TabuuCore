package nl.tabuu.tabuucore.command.register.exception;

public class CommandRegisterException extends RuntimeException {

    public CommandRegisterException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    public CommandRegisterException(String errorMessage) {
        super(errorMessage);
    }

}
