package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class CCommand extends BukkitCommand implements CommandExecutor {

    private Dictionary _language;

    private CCommand _parentCommand;
    private HashMap<String, CCommand> _subCommands;
    private List<Argument> _arguments;
    private CommandSenderType _requiredSenderType = null;

    public CCommand(PluginCommand command, CCommand parentCommand){
        this(command.getName(), parentCommand);
        this.setDescription(command.getDescription());
        this.setPermission(command.getPermission());
        this.setPermissionMessage(command.getPermissionMessage());
        this.setUsage(command.getUsage());
        this.setLabel(command.getLabel());
        this.setAliases(command.getAliases());
    }

    public CCommand(PluginCommand command){
        this(command, null);
    }

    public CCommand(String name, CCommand parentCommand) {
        super(name);
        _subCommands = new HashMap<>();
        _arguments = new ArrayList<>();
        _parentCommand = parentCommand;
        _language = TabuuCore.getInstance().getConfigurationManager().getConfiguration("lang").getDictionary("");
    }

    public CCommand(String name) {
        this(name, null);
    }

    public CCommand getParentCommand() {
        return _parentCommand;
    }

    public CCommand addSubCommand(String name, CCommand subCommand){
        _subCommands.put(name, subCommand);
        return this;
    }

    public CCommand setSubCommands(HashMap<String, CCommand> subCommands){
        _subCommands = subCommands;
        return this;
    }

    public CCommand addArgument(CommandArgumentType type, boolean required){
        Argument last = null;
        for(Argument argument : _arguments) last = argument;

        if(last == null)
            _arguments.add(new Argument(type, required));
        else if(!last.isRequired() && required)
            return this;
        else
            _arguments.add(new Argument(type, required));

        return this;
    }

    public CCommand setRequiredCommandSenderType(CommandSenderType type){
        _requiredSenderType = type;
        return this;
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] arguments) {
        onCommand(commandSender, this, label, arguments);
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] arguments){
        String[] convertedArgs = convertArguments(arguments);
        if(convertedArgs.length > 0 && _subCommands.get(convertedArgs[0]) != null){
            CCommand subCommand = _subCommands.get(convertedArgs[0]);
            subCommand.execute(commandSender, label, Arrays.copyOfRange(convertedArgs, 1, convertedArgs.length));
            return true;
        }

        if(this.getPermission() != null && !this.getPermission().equals("") && !commandSender.hasPermission(this.getPermission())){
            this.promptPermissionMessage(commandSender);
            return true;
        }
        if(_requiredSenderType != null && !_requiredSenderType.getClassType().isInstance(commandSender)){
            this.promptCommandSenderError(commandSender);
            return true;
        }

        List<CommandArgument<?>> commandArgumentList = new ArrayList<>();
        int i = 0;
        for(Argument argument : _arguments){
            try{
                CommandArgument<?> resultArgument = new CommandArgument(convertedArgs[i], argument.getType());

                for(Object object : resultArgument.getValues())
                    object.toString();

                commandArgumentList.add(resultArgument);
            }
            catch (ArrayIndexOutOfBoundsException e){
                if(!argument.isRequired())
                    addEmpty(commandArgumentList);
                else{
                    promptUsageMessage(commandSender);
                    return true;
                }
            }
            catch (Exception e){
                String error = null;
                switch (argument.getType()){
                    case PLAYER:
                        error = "PLAYER";
                        break;
                    case OFFLINE_PLAYER:
                        error = "OFFLINE_PLAYER";
                        break;
                    case INTEGER:
                        error = "INTEGER";
                        break;
                    case LONG:
                        error = "LONG";
                        break;
                    case DOUBLE:
                        error = "DOUBLE";
                        break;
                    case NULL:
                        break;
                    default:
                        error = "GENERAL";
                        break;
                }
                promptArgumentError(commandSender, convertedArgs[i], error);
                return true;
            }
            i++;
        }
        CommandResult result = onCommand(commandSender, commandArgumentList);

        switch (result){
            case SUCCESS:
                break;
            case NO_PERMISSION:
                promptPermissionMessage(commandSender);
                break;
            case WRONG_SYNTAX:
                promptUsageMessage(commandSender);
                break;
        }
        return true;
    }

    public abstract CommandResult onCommand(CommandSender sender, List<CommandArgument<?>> arguments);

    private void promptCommandSenderError(CommandSender sender){
        sender.sendMessage(_language.translate("ERROR_MESSAGE_INVALID_COMMANDSENDER_TYPE", "{SENDER_TYPE}", _requiredSenderType.name()));
    }

    private void promptUsageMessage(CommandSender sender){
        if(!this.getUsage().equals(""))
            sender.sendMessage(this.getUsage());
    }

    private void promptPermissionMessage(CommandSender sender){
        sender.sendMessage(_language.translate("ERROR_MESSAGE_MISSING_PERMISSION"));
    }

    private void promptArgumentError(CommandSender sender, String argument, String error){
        sender.sendMessage(_language.translate("ERROR_MESSAGE_CONVERT_" + error, "{ARG}", argument));
    }

    private void addEmpty(List<CommandArgument<?>> list){
        list.add(new CommandArgument(null, CommandArgumentType.NULL));
    }

    private String[] convertArguments(String[] args, int startArg, int stopArg){
        List<String> newArgs = new ArrayList<>();

        for(int i = 0; i < args.length; i++){
            if((i < startArg || i >= stopArg) || !args[i].startsWith("\""))
                newArgs.add(args[i]);
            else{
                String combinedString = "";
                while(i < args.length && !args[i].endsWith("\"")) {
                    combinedString += (args[i] + " ");
                    i++;
                }
                if(i < args.length)
                    combinedString += args[i];
                combinedString = combinedString.substring(1, combinedString.length() -1);

                newArgs.add(combinedString);
            }
        }

        return newArgs.stream().toArray(String[]::new);
    }

    public String[] convertArguments(String[] args){
        return convertArguments(args, 0, args.length);
    }

    class Argument{
        private CommandArgumentType _type;
        private boolean _required;

        public Argument(CommandArgumentType type, boolean required){
            _type = type;
            _required = required;
        }

        public CommandArgumentType getType() {
            return _type;
        }

        public boolean isRequired() {
            return _required;
        }
    }
}