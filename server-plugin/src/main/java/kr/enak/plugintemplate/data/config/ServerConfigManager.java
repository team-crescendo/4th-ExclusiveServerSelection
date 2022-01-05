package kr.enak.plugintemplate.data.config;

import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.data.DefaultDataManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ServerConfigManager<T extends ServerConfig> extends DefaultDataManager {
    private final File file;
    private T serverConfig;

    public ServerConfigManager(TemplatePlugin plugin) throws IOException {
        super(plugin);

        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.createNewFile();

            serverConfig = makeServerConfig(new HashMap<>());
        }
        loadData();
    }

    public abstract T makeServerConfig(Map<String, Object> map);

    @Override
    public void onSave() {
        super.onSave();
        this.saveData();
    }

    @Override
    public void saveData() {
        FileConfiguration config = new YamlConfiguration();
        serverConfig.serialize().forEach(config::set);

        try {
            config.save(file);
            TemplatePlugin.log("Saved config");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        serverConfig = makeServerConfig(config.getValues(true));
        saveData();
    }

    public T getServerConfig() {
        return serverConfig;
    }
}
