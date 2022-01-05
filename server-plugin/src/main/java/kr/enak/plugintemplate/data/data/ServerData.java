package kr.enak.plugintemplate.data.data;

import kr.enak.plugintemplate.models.StorableData;

import java.util.HashMap;
import java.util.Map;

public abstract class ServerData implements StorableData {
    public ServerData() {
        this(new HashMap<>());
    }

    public ServerData(Map<String, Object> map) {
        this.deserialize(map);
    }

    @Override
    public abstract Map<String, Object> serialize();

    public abstract void deserialize(Map<String, Object> map);
}
