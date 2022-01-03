package kr.enak.crescendo.exclusiveserverselection.bungee.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.commands.CommandSelection;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.ServerConfig;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data.ServerData;
import kr.enak.crescendo.exclusiveserverselection.bungee.plugin.handlers.JoinListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public final class ExclusiveServerSelection extends Plugin {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ServerConfig serverConfig;
    private static ServerData serverData;

    static {
        SimpleModule module = new SimpleModule();

        objectMapper.registerModule(module);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private File configFile;
    private File dataFile;

    private final Logger logger = getLogger();

    private static boolean ensurePath(File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            file.createNewFile();
            return true;
        }

        return false;
    }

    @Override
    public void onEnable() {
        // Load Data
        configFile = new File(getDataFolder(), "config.json");
        dataFile = new File(getDataFolder(), "data.json");
        loadConfig();
        loadData();

        // Register Listener
        {
            PluginManager pluginManager = getProxy().getPluginManager();
            Arrays.asList(
                    new JoinListener()
            ).forEach((listener) -> pluginManager.registerListener(this, listener));
        }

        PluginManager pluginManager = getProxy().getPluginManager();
        // Inject commands
        Arrays.asList(
                new CommandSelection()
        ).forEach(c -> pluginManager.registerCommand(this, c));
    }

    @Override
    public void onDisable() {
        saveConfig();
        saveData();
    }

    public void loadConfig() {
        try {
            logger.info("[DataManager] Ensure config path");
            if (ensurePath(configFile)) {
                logger.info("[DataManager] Empty config file detected, inject new config");
                serverConfig = new ServerConfig();
                saveConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            logger.info("[DataManager] Reading config");
            serverConfig = objectMapper.readValue(configFile, ServerConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            if (ensurePath(dataFile)) {
                serverData = new ServerData();
                saveData();
            }

            serverData = objectMapper.readValue(dataFile, ServerData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveConfig() {
        return writeToFile(configFile, serverConfig);
    }

    public boolean saveData() {
        return writeToFile(dataFile, serverData);
    }

    private <T> boolean writeToFile(File file, T obj) {
        logger.info(String.format("[DataManager] Writing %s to %s", obj, file));
        try {
            objectMapper.writeValue(file, obj);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ServerData getServerData() {
        return serverData;
    }

    public static ServerConfig getServerConfig() {
        return serverConfig;
    }
}
