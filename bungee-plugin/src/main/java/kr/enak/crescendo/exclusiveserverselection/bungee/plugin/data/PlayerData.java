package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.enak.crescendo.exclusiveserverselection.engine.models.ServerType;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class PlayerData {
    @JsonProperty private final String username;
    @JsonProperty private UUID uuid;
    @JsonProperty private ServerType serverType;

    public PlayerData() {
        this((String) null);
    }

    public PlayerData(ProxiedPlayer player) {
        this(
                player.getName(),
                player.getUniqueId()
        );
    }

    public PlayerData(ProxiedPlayer player, ServerType serverType) {
        this(
                player.getName(),
                player.getUniqueId(),
                serverType
        );
    }

    public PlayerData(String username) {
        this(username, null, ServerType.LOBBY);
    }

    public PlayerData(String username, UUID uuid) {
        this(username, uuid, ServerType.LOBBY);
    }

    public PlayerData(String username, UUID uuid, ServerType serverType) {
        this.username = username;
        this.uuid = uuid;
        this.serverType = serverType;
    }

    @JsonIgnore public UUID getUuid() {
        return uuid;
    }

    @JsonIgnore public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonIgnore public String getUsername() {
        return username;
    }

    @JsonIgnore public ServerType getServerType() {
        return serverType;
    }

    @JsonIgnore
    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
