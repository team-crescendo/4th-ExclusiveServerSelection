package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.server;

import kr.enak.plugintemplate.commands.CommandGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandServer extends CommandGroup implements CommandExecutor {
    public CommandServer() {
        super("server");

        registerCommand(new CommandMild());
        registerCommand(new CommandWild());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return this.onCommand(sender, label, args);
    }
}
