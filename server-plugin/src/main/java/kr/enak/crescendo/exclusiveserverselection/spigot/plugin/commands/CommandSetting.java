package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.setting.CommandModify;
import kr.enak.plugintemplate.commands.CommandGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandSetting extends CommandGroup implements CommandExecutor {
    public CommandSetting() {
        super("setting");

        registerCommand(new CommandModify());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return this.onCommand(sender, label, args);
    }
}
