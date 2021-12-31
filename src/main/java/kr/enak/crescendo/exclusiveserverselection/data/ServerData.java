package kr.enak.crescendo.exclusiveserverselection.data;

import java.util.HashMap;
import java.util.UUID;

public class ServerData {
    private final HashMap<UUID, PlayerData> playerDataMap;

    public ServerData() {
        this.playerDataMap = new HashMap<>();
    }

    public HashMap<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    public PlayerData getPlayerDataOrCreate(UUID uuid) {
        PlayerData data;

        if (!playerDataMap.containsKey(uuid))
            data = playerDataMap.put(uuid, new PlayerData(uuid));
        else
            data = playerDataMap.get(uuid);

        return data;
    }
}
