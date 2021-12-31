package kr.enak.crescendo.exclusiveserverselection.data;

import kr.enak.crescendo.exclusiveserverselection.models.ServerType;

import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private ServerType serverType;

    public PlayerData(UUID uuid) {
        this(uuid, ServerType.NONE);
    }

    public PlayerData(UUID uuid, ServerType serverType) {
        this.uuid = uuid;
        this.serverType = serverType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
