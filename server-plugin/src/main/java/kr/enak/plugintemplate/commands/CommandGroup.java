package kr.enak.plugintemplate.commands;

import kr.enak.plugintemplate.TemplatePlugin;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class CommandGroup extends CommandExecutor {
    private final List<CommandInterface> commandList = new ArrayList<>();

    public CommandGroup(String label, String... aliases) {
        super(label, aliases);
    }

    public final void registerCommand(CommandInterface command) {
        if (Objects.isNull(command) || this.commandList.contains(command)) return;

        this.commandList.add(command);
    }

    public final void unregisterCommand(CommandInterface command) {
        if (Objects.isNull(command) || !this.commandList.contains(command)) return;

        this.commandList.remove(command);
    }

    protected final List<CommandInterface> getCommandList() {
        return this.commandList;
    }

    protected void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage(TemplatePlugin.PREFIX + "-------- 하위 명령어 --------");
        commandList.forEach(commandInterface -> sender.sendMessage(TemplatePlugin.PREFIX + "/" + label + " " + commandInterface.getLabel()));
        sender.sendMessage(TemplatePlugin.PREFIX + "-----------------------------");
    }

    public boolean onCommandBefore(CommandSender sender, String label, String[] args) {
        return true;
    }

    public boolean onCommandAfter(CommandSender sender, String label, String[] args, CommandInterface executed, boolean result) {
        if (executed != null) return true;

        sendHelpMessage(sender, label);
        return true;
    }

    public boolean onCommandError(CommandSender sender, String label, String[] args, CommandInterface executor, Exception exception) {
        exception.printStackTrace();

        return true;
    }

    @Override
    public final boolean onCommand(CommandSender sender, String label, String[] args) {
        if (!onCommandBefore(sender, label, args)) return false;

        CommandInterface executed = null;
        boolean result = true;

        if (args.length != 0) {
            for (CommandInterface command : getCommandList()) {
                if (!command.hasResponsibility(args[0])) continue;

                String newLabel = label + " " + args[0];
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                try {
                    result = command.onCommand(sender, newLabel, newArgs);
                } catch (Exception e) {
                    return onCommandError(sender, label, args, command, e);
                }
                executed = command;
            }
        }

        return onCommandAfter(sender, label, args, executed, result);
    }
}
