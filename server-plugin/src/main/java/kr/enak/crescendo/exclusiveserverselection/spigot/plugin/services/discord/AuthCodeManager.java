package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.services.discord;

import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.ExSSSpigotPlugin;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config.ServerConfigManager;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.PlayerData;
import kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data.ServerDataManager;
import kr.enak.plugintemplate.TemplatePlugin;
import kr.enak.plugintemplate.models.DefaultResourceManager;
import net.dv8tion.jda.api.entities.User;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class AuthCodeManager extends DefaultResourceManager {
    private static final Random random = new Random();
    private final ExSSSpigotPlugin plugin;
    private final ServerDataManager dataManager;
    private final ServerConfigManager configManager;

    public AuthCodeManager(TemplatePlugin plugin) {
        super(plugin);

        this.plugin = (ExSSSpigotPlugin) plugin;
        this.dataManager = TemplatePlugin.getResourceManager(ServerDataManager.class);
        this.configManager = TemplatePlugin.getResourceManager(ServerConfigManager.class);
    }

    private static String generateAuthCode(int length) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++)
            code.append(((Integer) (random.nextInt(10))).toString());

        return code.toString();
    }

    private Map<String, UUID> getAuthCodeMap() {
        return this.dataManager.getServerData().getAuthCodeMap();
    }

    public String enqueueCode(UUID uuid) {
        if (getAuthCodeMap().containsValue(uuid))
            return getAuthCodeMap().entrySet().stream().filter(set -> set.getValue() == uuid).findFirst().get().getKey();
        return enqueueCode(generateAuthCode(configManager.getServerConfig().getDiscordConfig().getAuthCodeLength()), uuid);
    }

    public String enqueueCode(String code, UUID uuid) {
        if (getAuthCodeMap().get(code) == uuid)
            return code;

        getAuthCodeMap().put(code, uuid);
        return code;
    }

    public UUID tryUseAuthCode(String code) {
        return getAuthCodeMap().getOrDefault(code, null);
    }

    public boolean completeAuth(String code, User user) {
        try {
            UUID uuid = getAuthCodeMap().get(code);

            PlayerData playerData = this.dataManager.getServerData().getPlayerDataMap().getOrDefault(uuid, new PlayerData(uuid));
            playerData.setDiscordId(user.getIdLong());

            this.dataManager.getServerData().getPlayerDataMap().put(uuid, playerData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
