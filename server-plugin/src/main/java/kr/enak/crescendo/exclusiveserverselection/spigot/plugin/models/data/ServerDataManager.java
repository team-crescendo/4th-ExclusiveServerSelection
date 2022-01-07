package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.plugintemplate.TemplatePlugin;

import java.io.IOException;
import java.util.Map;

public class ServerDataManager extends kr.enak.plugintemplate.data.data.ServerDataManager<ServerData> {
    private final ExSSSpigotPlugin plugin;

    public ServerDataManager(TemplatePlugin plugin) throws IOException {
        super(plugin);
        this.plugin = (ExSSSpigotPlugin) plugin;
    }

    @Override
    public ServerData makeServerData(Map<String, Object> map) {
        return new ServerData(map);
    }
}
