package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.portal.CommandPortal;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.server.CommandServer;
import kr.enak.plugintemplate.TemplatePlugin;

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
}
