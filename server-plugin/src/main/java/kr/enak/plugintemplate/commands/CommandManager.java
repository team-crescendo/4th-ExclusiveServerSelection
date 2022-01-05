package kr.enak.plugintemplate.commands;

import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;

public abstract class CommandManager<T extends CommandGroup & CommandExecutor, P extends TemplatePlugin> extends DefaultResourceManager {
    private final T command;

    public CommandManager(P plugin, T command) {
        super(plugin);

        this.command = command;
    }

    public T getCommand() {
        return command;
    }

    public abstract void registerSubCommands(T command);

    @Override
    public void onInit() {
        super.onInit();

        this.registerSubCommands(command);
        ArrayList<String> labels = new ArrayList<>(command.getAliasList());
        labels.add(0, command.getLabel());
        labels.forEach(label -> Bukkit.getPluginCommand(label).setExecutor(command));
    }
}
