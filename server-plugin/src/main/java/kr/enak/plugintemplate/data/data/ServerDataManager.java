package kr.enak.plugintemplate.data.data;

import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.data.DefaultDataManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ServerDataManager<T extends ServerData> extends DefaultDataManager {
    private final File file;
    private T serverData;

    public ServerDataManager(TemplatePlugin plugin) throws IOException {
        super(plugin);

        if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
        file = new File(plugin.getDataFolder(), "data.yml");
        if (!file.exists()) {
            file.createNewFile();

            serverData = makeServerData(new HashMap<>());
        }
        loadData();
    }

    public abstract T makeServerData(Map<String, Object> map);

    @Override
    public void onSave() {
        super.onSave();
        this.saveData();
    }

    @Override
    public void saveData() {
        FileConfiguration config = new YamlConfiguration();
        serverData.serialize().forEach(config::set);
        try {
            config.save(file);
            TemplatePlugin.log("Saved Data");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.serverData = makeServerData(config.getValues(true));
        saveData();
    }

    public T getServerData() {
        return serverData;
    }
}
