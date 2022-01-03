package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;

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

        if (!playerDataMap.containsKey(player.getName()) && createIfAbsent) {
            data = new PlayerData(player);
            playerDataMap.put(player.getName(), data);
        }
        else
            data = playerDataMap.get(player.getName());

        return data;
    }

    @JsonIgnore public PlayerData getPlayerDataOrCreate(String username, boolean createIfAbsent) {
        PlayerData data;

        if (!playerDataMap.containsKey(username) && createIfAbsent) {
            data = new PlayerData(username);
            playerDataMap.put(username, data);
        }
        else
            data = playerDataMap.get(username);

        return data;
    }
}
