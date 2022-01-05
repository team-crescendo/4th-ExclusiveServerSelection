package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands;

import kr.enak.plugintemplate.commands.CommandGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandSelection extends CommandGroup implements CommandExecutor {
    public CommandSelection() {
        super("select");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return this.onCommand(sender, label, args);
    }
}
