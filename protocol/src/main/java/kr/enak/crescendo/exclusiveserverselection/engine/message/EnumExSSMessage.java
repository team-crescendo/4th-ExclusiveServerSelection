package kr.enak.crescendo.exclusiveserverselection.engine.message;

import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import kr.enak.crescendo.exclusiveserverselection.engine.message.request.SpigotRequestSelectServer;
import kr.enak.crescendo.exclusiveserverselection.engine.message.response.BungeeSentPlayer;

import java.util.HashMap;

public enum EnumExSSMessage {
    SPIGOT_REQUEST_SELECT_SERVER(0x01, SpigotRequestSelectServer.class),
    BUNGEE_SENT_PLAYER(0x02, BungeeSentPlayer.class);

    private static final HashMap<Byte, EnumExSSMessage> idMap = new HashMap<>();

    static {
        for (EnumExSSMessage value : values()) {
            idMap.put(value.getId(), value);
        }
    }

    private final byte id;
    private final Class<? extends IMessage> messageClazz;

    EnumExSSMessage(int id, Class<? extends IMessage> clazz) {
        this((byte) id, clazz);
    }

    EnumExSSMessage(byte id, Class<? extends IMessage> clazz) {
        this.id = id;
        this.messageClazz = clazz;
    }

    public static EnumExSSMessage getById(Byte id) {
        return idMap.get(id);
    }

    public byte getId() {
        return id;
    }

    public Class<? extends IMessage> getMessageClazz() {
        return messageClazz;
    }
}
