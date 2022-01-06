package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.plugintemplate.TemplatePlugin;

import java.io.IOException;
import java.util.Map;

public class ServerConfigManager extends kr.enak.plugintemplate.data.config.ServerConfigManager<ServerConfig> {
    public ServerConfigManager() throws IOException {
        this(ExSSSpigotPlugin.getInstance());
    }

    public ServerConfigManager(TemplatePlugin plugin) throws IOException {
        super(plugin);
    }

    @Override
    public ServerConfig makeServerConfig(Map<String, Object> map) {
        return new ServerConfig(map);
    }
}
