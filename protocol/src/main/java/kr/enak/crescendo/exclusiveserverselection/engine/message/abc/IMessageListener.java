package kr.enak.crescendo.exclusiveserverselection.engine.message.abc;

public interface IMessageListener {
    byte getPacketId();

    void onPacketReceived(Object player, IMessage message);
}
