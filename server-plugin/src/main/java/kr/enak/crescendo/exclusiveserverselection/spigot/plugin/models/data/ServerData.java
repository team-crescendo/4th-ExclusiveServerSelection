package kr.enak.crescendo.exclusiveserverselection.spigot.plugin.models.data;

import java.util.*;

public class ServerData extends kr.enak.plugintemplate.data.data.ServerData {
    private final Map<UUID, PlayerData> playerDataMap;
    private final Map<String, UUID> authCodeMap;

    public ServerData(Map<String, Object> map) {
        this.playerDataMap = new HashMap<>();
        this.authCodeMap = new HashMap<>();

        ((List<PlayerData>) map.getOrDefault("playerDataMap", new ArrayList<>()))
                .forEach(playerData -> playerDataMap.put(playerData.getUuid(), playerData));
//        ((Map<String, UUID>) map.getOrDefault("authCodeMap", new HashMap<>())).forEach(this.authCodeMap::put);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("playerDataMap", new ArrayList<>(this.playerDataMap.values()));
//        map.put("authCodeMap", this.authCodeMap);

        return map;
    }

    @Override
    public void deserialize(Map<String, Object> map) {

    }

    public Map<UUID, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    public Map<String, UUID> getAuthCodeMap() {
        return authCodeMap;
    }
}
