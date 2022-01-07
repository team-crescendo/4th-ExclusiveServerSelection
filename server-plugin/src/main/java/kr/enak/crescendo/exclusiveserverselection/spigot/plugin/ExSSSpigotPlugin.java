package kr.enak.crescendo.exclusiveserverselection.spigot.plugin;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.CommandManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.PlayerJoinListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.PortalListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.ServerDataManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network.NetworkManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.AuthCodeManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord.DiscordManager;
import kr.enak.plugintemplate.TemplatePlugin;

import java.util.Arrays;

public class ExSSSpigotPlugin extends TemplatePlugin {
    private static ExSSSpigotPlugin instance;

    public static ExSSSpigotPlugin getInstance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return "[ §1TeamCrescendo§f ] ";
    }

    @Override
    public void onEnable() {
        instance = this;

        this.DEFAULT_MANAGERS.addAll(Arrays.asList(
                NetworkManager.class,
                CommandManager.class,
                ServerConfigManager.class,
                ServerDataManager.class,
                AuthCodeManager.class,
                DiscordManager.class
        ));
        this.DEFAULT_LISTENERS.addAll(Arrays.asList(
                PortalListener.class,
                PlayerJoinListener.class
        ));

        super.onEnable();
    }
}
