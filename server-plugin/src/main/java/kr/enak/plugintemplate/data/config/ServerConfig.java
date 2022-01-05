package kr.enak.plugintemplate.data.config;

import kr.enak.plugintemplate.models.StorableData;

import java.util.HashMap;
import java.util.Map;

public abstract class ServerConfig implements StorableData {
    public ServerConfig() {
        this(new HashMap<>());
    }

    public ServerConfig(Map<String, Object> map) {
        this.deserialize(map);
    }

    public abstract void deserialize(Map<String, Object> map);

    @Override
    public abstract Map<String, Object> serialize();
}
