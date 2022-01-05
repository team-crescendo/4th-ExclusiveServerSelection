package kr.enak.crescendo.exclusiveserverselection.bungee.plugin.events.network;

import kr.enak.crescendo.exclusiveserverselection.engine.message.abc.IMessage;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.plugin.Event;

public class ExSSPacketReceivedEvent<T extends IMessage> extends Event {
    private final Connection connection;
    private final T message;

    public ExSSPacketReceivedEvent(Connection connection, T message) {
        this.connection = connection;
        this.message = message;
    }

    public Connection getConnection() {
        return connection;
    }

    public T getMessage() {
        return message;
    }
}
