# TabuuCore
TabuuCore is a library and framework for creating Spigot plugins.


## Code Examples
Here you can find some helpful code examples


### Command
In this chapter you can read on how to use the command creation features. You can read more about frequently used command related objects in the [Command package](http://f.tabuu.nl/spigot/tabuucore/javadoc/nl/tabuu/tabuucore/command/package-summary.html).

#### Registering
To register a command you can use the following code in your plugin class:
```java
getCommand("example").setExecutor(new ExampleCommand());
```

#### Handling execution and arguments
In the following example a command is created that spams a player.

```java
public class ExampleCommand extends Command {

    public ExampleCommand() {
        // Generating the command class based on the command registered in the plugin.yml.
        super("example");

        // Creating a new OrderedArgumentConverter which converts string arguments to the desired objects.
        OrderedArgumentConverter converter = new OrderedArgumentConverter();

        // Setting the sequence/order of the objects as they should appear in the command.
        converter.setSequence(ArgumentType.STRING, ArgumentType.INTEGER);

        // Adds a parameter which can repeat itself infinitely, similar to how Varargs work in Java.
        converter.setParameter(ArgumentType.PLAYER);

        // Sets the created OrderedArgumentConverter as ArgumentConverter for the command.
        setArgumentConverter(converter);

        // Sets the required SenderType (ANY by default).
        setRequiredSenderType(SenderType.PLAYER);
    }

    /**
     * Called when the command is executed.
     *
     * @param sender    The executor of the command.
     * @param arguments The converted arguments.
     *                  This is list is at least the size of the sequence, but might be longer depending on the parameters.
     *                  If the executor failed to provide an argument from sequence, the optional will be empty.
     *                  If an argument could not be converted, the executor will receive an error message.
     *                  Additional parameters are always present in the Optional.
     * @return The result of the command.
     */
    @Override
    protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
        // Checks if all arguments are present.
        for (Optional<?> argument : arguments) {
            if (!argument.isPresent())
                return CommandResult.WRONG_SYNTAX;
        }

        // Gets the first converted argument and casts it to the correct type.
        String message = (String) arguments.get(0).get();

        // Gets the second converted argument and casts it to the correct type.
        int count = (Integer) arguments.get(1).get();

        // Exits the command if no players are provided.
        if (arguments.size() == 2)
            return CommandResult.SUCCESS;

        // Creates a list of the additional parameters.
        List<Optional<?>> additionalParameters = arguments.subList(2, arguments.size());

        // Converts the additional parameters to a list.
        List<Player> recipients = new ArrayList<>();
        for (Optional<?> argument : additionalParameters) {
            Player player = (Player) argument.get();
            recipients.add(player);
        }

        // Do something with the variables
        for (Player player : recipients) {
            for (int i = 0; i < count; i++)
                player.sendMessage(message);
        }

        // Returns the result of the command.
        return CommandResult.SUCCESS;
    }
}
```

#### Sub-commands
Note that sub-commands, when used like this, have to be specified in the plugin.yml by there full command label.

```java
public class ExampleCommand extends Command {

    public ExampleCommand() {
        // Generating the command class based on the command registered in the plugin.yml.
        super("example");

        // Adds and registers a sub-command to the command.
        addSubCommand("sub", new ExampleSubCommand(this));
    }

    @Override
    protected CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments) {
        // Executes when the main command is executed.
        return CommandResult.SUCCESS;
    }
}

class ExampleSubCommand extends Command {

    protected ExampleSubCommand(Command parent) {
        // Generating the command class based on the command registered in the plugin.yml.
        super("example sub", parent);
    }

    @Override
    protected CommandResult onCommand(CommandSender commandSender, List<Optional<?>> list) {
        // Executes when '/example sub' or one of this sub-command's aliases is executed.
        return CommandResult.SUCCESS;
    }
}
```


## License
Licensed under the [GNU General Public License v3.0](LICENSE) license.
