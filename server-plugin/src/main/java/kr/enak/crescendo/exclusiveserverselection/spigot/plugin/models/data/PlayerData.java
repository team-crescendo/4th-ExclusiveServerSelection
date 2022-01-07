package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data;

import kr.enak.plugintemplate.models.StorableData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData implements StorableData {
    private final UUID uuid;
    private Long discordId;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.discordId = 0L;
    }

    public PlayerData(Map<String, Object> map) {
        this.uuid = UUID.fromString((String) map.get("uuid"));
        this.discordId = (Long) map.getOrDefault("discordId", 0L);
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("uuid", this.uuid.toString());
        map.put("discordId", this.discordId);

        return map;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getDiscordId() {
        return discordId;
    }

    public void setDiscordId(Long discordId) {
        this.discordId = discordId;
    }
}
