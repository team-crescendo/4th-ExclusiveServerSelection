package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.portal.CommandPortal;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.server.CommandServer;
import kr.enak.plugintemplate.TemplatePlugin;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager extends kr.enak.plugintemplate.commands.CommandManager<CommandSelection, ExSSSpigotPlugin> {
    public CommandManager() {
        this(ExSSSpigotPlugin.getInstance());
    }

    public CommandManager(TemplatePlugin plugin) {
        this((ExSSSpigotPlugin) plugin, new CommandSelection());
    }

    public CommandManager(ExSSSpigotPlugin plugin, CommandSelection command) {
        super(plugin, command);
    }

    @Override
    public void registerSubCommands(CommandSelection command) {
        command.registerCommand(new CommandServer());
        command.registerCommand(new CommandPortal());
    }

    @Override
    public void onInit() {
        super.onInit();

        Arrays.asList(
                new CommandSetting()
        ).forEach(command -> {
            ArrayList<String> labels = new ArrayList<>(command.getAliasList());
            labels.add(0, command.getLabel());
            labels.forEach(label -> Bukkit.getPluginCommand(label).setExecutor(command));
        });
    }
}
