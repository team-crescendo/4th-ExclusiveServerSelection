package kr.enak.crescendo.exclusiveserverselection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.enak.crescendo.exclusiveserverselection.models.ServerType;

import java.util.UUID;

public class PlayerData {
    @JsonProperty
    private final UUID uuid;
    @JsonProperty
    private ServerType serverType;

    public PlayerData(UUID uuid) {
        this(uuid, ServerType.NONE);
    }

    public PlayerData(UUID uuid, ServerType serverType) {
        this.uuid = uuid;
        this.serverType = serverType;
    }

    @JsonIgnore
    public UUID getUuid() {
        return uuid;
    }

    @JsonIgnore
    public ServerType getServerType() {
        return serverType;
    }

    @JsonIgnore
    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
