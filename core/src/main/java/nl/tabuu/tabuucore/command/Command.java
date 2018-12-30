package nl.tabuu.tabuucore.command;

import nl.tabuu.tabuucore.TabuuCore;
import nl.tabuu.tabuucore.util.Dictionary;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public abstract class Command extends BukkitCommand implements CommandExecutor {

    private Command _parent;
    private HashMap<String, Command> _subCommandMap;
    private SenderType _requiredSenderType;
    private ArgumentConverter _argumentConverter;

    private Dictionary _local;

    protected Command(String name){
        this(Bukkit.getPluginCommand(name));
    }

    protected Command(String name, Command parent) {
        this(Bukkit.getPluginCommand(name), parent);
    }

    protected Command(PluginCommand command){
        this(command, null);
    }

    protected Command(PluginCommand command, Command parent){
        super(command.getName());
        this.setDescription(command.getDescription());
        this.setPermission(command.getPermission());
        this.setPermissionMessage(command.getPermissionMessage());
        this.setUsage(command.getUsage());
        this.setLabel(command.getLabel());
        this.setAliases(command.getAliases());

        _parent = parent;
        _requiredSenderType = null;
        _subCommandMap = new HashMap<>();
        _argumentConverter = new OrderedArgumentConverter();

        _local = TabuuCore.getInstance().getConfigurationManager().getConfiguration("lang").getDictionary("");
    }

    protected abstract CommandResult onCommand(CommandSender sender, List<Optional<?>> arguments);

    @Override
    public boolean execute(CommandSender sender, String label, String[] arguments) {
        if(arguments.length > 0 && _subCommandMap.containsKey(arguments[0])){
            Command command = _subCommandMap.get(arguments[0]);
            command.execute(sender, label, Arrays.copyOfRange(arguments, 1, arguments.length));
            return true;
        }

        if(_requiredSenderType != null && !_requiredSenderType.getClassType().isInstance(sender)){
            sender.sendMessage(_local.translate("ERROR_COMMAND_INVALID_SENDER_TYPE", "{TYPE}", _requiredSenderType.name()));
            return true;
        }

        if(getPermission() != null && !getPermission().equals("") && !sender.hasPermission(getPermission())){
            if(getPermissionMessage() != null && !getPermissionMessage().equals(""))
                sender.sendMessage(getPermissionMessage());
            else
                sender.sendMessage(_local.translate("ERROR_INSUFFICIENT_PERMISSION", "{PERM}", getPermission()));

            return true;
        }

        List<Optional<?>> convertedArguments = _argumentConverter.convert(sender, arguments);

        if(convertedArguments == null)
            return true;

        CommandResult result = onCommand(sender, convertedArguments);

        switch (result){
            case NO_PERMISSION:
                if(getPermissionMessage() != null || !getPermission().equals(""))
                    sender.sendMessage(getPermissionMessage());
                else
                    sender.sendMessage(_local.translate("ERROR_INSUFFICIENT_PERMISSION", "{PERM}", getPermission()));
            case SUCCESS:
                return true;

            default:
            case WRONG_SYNTAX:
                return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] arguments) {
        return execute(sender, bukkitCommand.getLabel(), arguments);
    }

    protected void setArgumentConverter(ArgumentConverter sequence){
        _argumentConverter = sequence;
    }

    protected ArgumentConverter getArgumentConverter(){
        return _argumentConverter;
    }

    protected boolean hasRequiredSenderType(){
        return _requiredSenderType != null;
    }

    protected void setRequiredSenderType(SenderType type){
        _requiredSenderType = type;
    }

    protected SenderType getRequiredSenderType(){
        return _requiredSenderType;
    }

    protected boolean hasParent(){
        return _parent != null;
    }

    protected Command getParent(){
        return _parent;
    }

    protected void addSubCommand(String label, Command command){
        _subCommandMap.put(label, command);
    }

    protected Command getSubCommand(String label){
        return _subCommandMap.get(label);
    }


}
