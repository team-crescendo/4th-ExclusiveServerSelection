package kr.enak.crescendo.exclusiveserverselection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.UUID;

public class ServerData {
    @JsonProperty
    private final HashMap<String, PlayerData> playerDataMap;

    public ServerData() {
        this.playerDataMap = new HashMap<>();
    }

    @JsonIgnore
    public HashMap<String, PlayerData> getPlayerDataMap() {
        return playerDataMap;
    }

    @JsonIgnore public PlayerData getPlayerDataOrCreate(ProxiedPlayer player, boolean createIfAbsent) {
        PlayerData data;

        if (!playerDataMap.containsKey(player.getName()) && createIfAbsent)
            data = playerDataMap.put(player.getName(), new PlayerData(player));
        else
            data = playerDataMap.get(player.getName());

        return data;
    }

    @JsonIgnore public PlayerData getPlayerDataOrCreate(String username, boolean createIfAbsent) {
        PlayerData data;

        if (!playerDataMap.containsKey(username) && createIfAbsent)
            data = playerDataMap.put(username, new PlayerData(username));
        else
            data = playerDataMap.get(username);

        return data;
    }
}
