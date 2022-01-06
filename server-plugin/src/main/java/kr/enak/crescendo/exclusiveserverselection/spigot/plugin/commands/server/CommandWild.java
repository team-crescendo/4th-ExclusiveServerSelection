package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.server;

import kr.enak.crescendo.exclusiveserverselection.engine.message.request.SpigotRequestSelectServer;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network.NetworkManager;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.commands.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWild extends CommandExecutor {
    private final NetworkManager networkManager;

    public CommandWild() {
        super("WILD");

        networkManager = TemplatePlugin.getResourceManager(NetworkManager.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        networkManager.sendMessage((Player) sender, new SpigotRequestSelectServer(
                sender.getName(), ServerType.WILD
        ));

        return true;
    }
}
