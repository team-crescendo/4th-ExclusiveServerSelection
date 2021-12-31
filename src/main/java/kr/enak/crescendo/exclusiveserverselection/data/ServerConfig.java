package kr.enak.crescendo.exclusiveserverselection.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerConfig {
    @JsonProperty
    private boolean isServerExclusive;

    public ServerConfig() {
        this(false);
    }

    public ServerConfig(
            boolean isServerExclusive
    ) {
        this.isServerExclusive = isServerExclusive;
    }

    @JsonIgnore
    public boolean isServerExclusive() {
        return isServerExclusive;
    }

    @JsonIgnore
    public void setServerExclusive(boolean serverExclusive) {
        isServerExclusive = serverExclusive;
    }
}
