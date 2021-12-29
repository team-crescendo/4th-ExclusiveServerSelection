package kr.enak.crescendo.exclusiveserverselection;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.enak.crescendo.exclusiveserverselection.data.ServerConfig;
import kr.enak.crescendo.exclusiveserverselection.data.ServerData;
import kr.enak.crescendo.exclusiveserverselection.handlers.JoinListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class ExclusiveServerSelection extends Plugin {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ServerConfig serverConfig;
    private static ServerData serverData;
    private File configFile;
    private File dataFile;

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }

    public static ServerData getServerData() {
        return serverData;
    }

    @Override
    public void onEnable() {
        // Load Data
        configFile = new File(getDataFolder(), "config.json");
        dataFile = new File(getDataFolder(), "data.json");
        try {
            serverConfig = objectMapper.readValue(configFile, ServerConfig.class);
            serverData = objectMapper.readValue(dataFile, ServerData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register Listener
        {
            PluginManager pluginManager = getProxy().getPluginManager();
            Arrays.asList(
                    new JoinListener()
            ).forEach((listener) -> pluginManager.registerListener(this, listener));
        }
    }

    @Override
    public void onDisable() {
        saveData();
    }

    public boolean saveData() {
        try {
            objectMapper.writeValue(configFile, serverConfig);
            objectMapper.writeValue(dataFile, serverData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
