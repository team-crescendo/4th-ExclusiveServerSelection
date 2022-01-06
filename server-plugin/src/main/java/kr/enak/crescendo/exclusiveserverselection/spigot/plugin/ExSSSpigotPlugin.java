package kr.enak.crescendo.exclusiveserverselection.spigot.plugin;

import kr.enak.crescendo.exclusiveserverselection.engine.ExSSEngine;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.commands.CommandManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.listeners.PortalListener;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.network.NetworkManager;
import kr.enak.plugintemplate.TemplatePlugin;

import java.util.Arrays;

public class ExSSSpigotPlugin extends TemplatePlugin {
    private static ExSSSpigotPlugin instance;

    public static ExSSSpigotPlugin getInstance() {
        return instance;
    }

    @Override
    public String getPrefix() {
        return "[TeamCrescendo] ";
    }

    @Override
    public void onEnable() {
        instance = this;

        this.DEFAULT_MANAGERS.addAll(Arrays.asList(
                NetworkManager.class,
                CommandManager.class,
                ServerConfigManager.class
        ));
        this.DEFAULT_LISTENERS.addAll(Arrays.asList(
                PortalListener.class
        ));

        super.onEnable();

        getServer().getMessenger().registerOutgoingPluginChannel(
                this,
                ExSSEngine.pluginChannelName
        );
        getServer().getMessenger().registerIncomingPluginChannel(
                this,
                ExSSEngine.pluginChannelName,
                TemplatePlugin.getResourceManager(NetworkManager.class)
        );
    }
}
