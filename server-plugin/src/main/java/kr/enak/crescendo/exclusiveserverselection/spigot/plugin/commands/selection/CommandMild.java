package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.selection;

import kr.enak.crescendo.exclusiveserverselection.engine.message.request.SpigotRequestSelectServer;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network.NetworkManager;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.commands.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMild extends CommandExecutor {
    private final NetworkManager networkManager;

    public CommandMild() {
        super("MILD");

        networkManager = TemplatePlugin.getResourceManager(NetworkManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        networkManager.sendMessage((Player) sender, new SpigotRequestSelectServer(
                sender.getName(), ServerType.MILD
        ));

        return true;
    }
}
