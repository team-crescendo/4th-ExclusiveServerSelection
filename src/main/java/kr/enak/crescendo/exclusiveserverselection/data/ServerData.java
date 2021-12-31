package kr.enak.crescendo.exclusiveserverselection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.UUID;

public class ServerData {
    @JsonProperty
    private final HashMap<UUID, PlayerData> playerDataMap;

    public ServerData() {
        this.playerDataMap = new HashMap<>();
    }

    @JsonIgnore
    public HashMap<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    @JsonIgnore
    public PlayerData getPlayerDataOrCreate(UUID uuid) {
        PlayerData data;

        if (!playerDataMap.containsKey(uuid))
            data = playerDataMap.put(uuid, new PlayerData(uuid));
        else
            data = playerDataMap.get(uuid);

        return data;
    }
}
