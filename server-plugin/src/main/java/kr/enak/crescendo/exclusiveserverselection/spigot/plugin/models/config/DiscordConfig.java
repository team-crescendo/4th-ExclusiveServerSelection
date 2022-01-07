package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.config;

import kr.enak.plugintemplate.models.StorableData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscordConfig implements StorableData {
    private @NotNull String token;
    private @NotNull Long verificationChannelId;
    private @NotNull Long adminRoleId;
    private @NotNull Long mildRoleId;
    private @NotNull Long wildRoleId;
    private @NotNull Integer authCodeLength;

    public DiscordConfig() {
        this(new HashMap<>());
    }

    public DiscordConfig(Map<String, Object> map) {
        this.token = (String) map.getOrDefault("token", "");
        this.verificationChannelId = (Long) map.getOrDefault("verificationChannelId", 928958570245025832L);
        this.adminRoleId = (Long) map.getOrDefault("adminRoleId", 405986396310994945L);
        this.mildRoleId = (Long) map.getOrDefault("mildRoleId", 928967362449915914L);
        this.wildRoleId = (Long) map.getOrDefault("wildRoleId", 928967480926429194L);
        this.authCodeLength = (Integer) map.getOrDefault("authCodeLength", 6);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("token", this.token);
        map.put("verificationChannelId", this.verificationChannelId);
        map.put("adminRoleId", this.adminRoleId);
        map.put("mildRoleId", this.mildRoleId);
        map.put("wildRoleId", this.wildRoleId);
        map.put("authCodeLength", this.authCodeLength);

        return map;
    }

    public @NotNull String getToken() {
        return token;
    }

    public @NotNull Long getVerificationChannelId() {
        return verificationChannelId;
    }

    public @NotNull Long getAdminRoleId() {
        return adminRoleId;
    }

    public Long getMildRoleId() {
        return mildRoleId;
    }

    public Long getWildRoleId() {
        return wildRoleId;
    }

    public Integer getAuthCodeLength() {
        return authCodeLength;
    }
}
